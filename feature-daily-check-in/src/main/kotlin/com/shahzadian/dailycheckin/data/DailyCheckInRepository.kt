package com.shahzadian.dailycheckin.data

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.dailycheckin.db.DailyCheckInDatabase
import com.shahzadian.dailycheckin.db.DailyCheckInEntity
import com.shahzadian.dailycheckin.domain.DailyCheckInMood
import com.shahzadian.dailycheckin.domain.DailyCheckInState
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

internal interface DailyCheckInRepository {

    suspend fun add(state: DailyCheckInState)

    suspend fun getForDay(date: LocalDate): DailyCheckInState?

    suspend fun clearAll()
}

internal class DailyCheckInRepositoryImpl @Inject constructor(
    private val dailyCheckInDatabase: DailyCheckInDatabase,
    private val coroutineContexts: CoroutineContexts,
) : DailyCheckInRepository {

    private var entities: List<DailyCheckInEntity>? = null

    private val mutex: Mutex by lazyUnsafe { Mutex() }

    override suspend fun add(state: DailyCheckInState) {
        mutex.withLock {
            withContext(coroutineContexts.io) {
                dailyCheckInDatabase.dailyCheckInDao().insert(state.toEntity())
                validateEntities()
            }
        }
    }

    override suspend fun getForDay(date: LocalDate): DailyCheckInState? {
        mutex.withLock {
            return withContext(coroutineContexts.io) {
                if (entities == null) validateEntities()
                entities?.firstOrNull { it.date == date }?.toState()
            }
        }
    }

    override suspend fun clearAll() {
        withContext(coroutineContexts.io) {
            dailyCheckInDatabase.dailyCheckInDao().clear()
            entities = null
        }
    }

    private suspend fun validateEntities() {
        withContext(coroutineContexts.io) {
            entities = dailyCheckInDatabase.dailyCheckInDao().getAll()
        }
    }

    private fun DailyCheckInEntity.toState(): DailyCheckInState? {
        return DailyCheckInState(
            mood = DailyCheckInMood.entries
                .firstOrNull { it.name == this.mood }
                ?: return null,
            date = this.date,
        )
    }

    private fun DailyCheckInState.toEntity(): DailyCheckInEntity {
        return DailyCheckInEntity(
            mood = this.mood.name,
            date = this.date,
        )
    }
}

package com.shahzadian.dailycheckin.data

import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.dailycheckin.db.DailyCheckInDatabase
import com.shahzadian.dailycheckin.db.DailyCheckInEntity
import com.shahzadian.dailycheckin.domain.DailyCheckInMood
import com.shahzadian.dailycheckin.domain.DailyCheckInState
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

    override suspend fun add(state: DailyCheckInState) {
        withContext(coroutineContexts.io) {
            dailyCheckInDatabase.dailyCheckInDao().insert(state.toEntity())
        }
    }

    override suspend fun getForDay(date: LocalDate): DailyCheckInState? {
        return withContext(coroutineContexts.io) {
            dailyCheckInDatabase.dailyCheckInDao().getForDay(date)?.toState()
        }
    }

    override suspend fun clearAll() {
        withContext(coroutineContexts.io) {
            dailyCheckInDatabase.dailyCheckInDao().clear()
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

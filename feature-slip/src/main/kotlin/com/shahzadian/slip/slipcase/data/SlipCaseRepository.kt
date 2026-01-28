package com.shahzadian.slip.slipcase.data

import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.slip.slipcase.db.SlipCaseDatabase
import com.shahzadian.slip.slipcase.db.SlipCaseEntity
import com.shahzadian.slip.slipcase.domain.SlipCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface SlipCaseRepository {

    suspend fun getSlipCases(): List<SlipCase>

    suspend fun insertSlipCase(slipCase: SlipCase)

    suspend fun deleteSlipCasesEarlierThanMillis(millis: Long)

    suspend fun clearAll()
}

internal class SlipCaseRepositoryImpl @Inject constructor(
    private val coroutineContexts: CoroutineContexts,
    private val slipCaseDatabase: SlipCaseDatabase,
) : SlipCaseRepository {

    override suspend fun getSlipCases(): List<SlipCase> {
        return withContext(coroutineContexts.io) {
            slipCaseDatabase.slipCaseDao().getAll().map { it.toDomain() }
        }
    }

    override suspend fun insertSlipCase(slipCase: SlipCase) {
        withContext(coroutineContexts.io) {
            slipCaseDatabase.slipCaseDao().insert(slipCase.toEntity())
        }
    }

    override suspend fun deleteSlipCasesEarlierThanMillis(millis: Long) {
        withContext(coroutineContexts.io) {
            val dao = slipCaseDatabase.slipCaseDao()
            dao.getAll()
                .filter { it.slipDateMillis < millis }
                .forEach { dao.deleteByMillis(it.slipDateMillis) }
        }
    }

    override suspend fun clearAll() {
        withContext(coroutineContexts.io) {
            slipCaseDatabase.clearAllTables()
        }
    }

    private fun SlipCaseEntity.toDomain(): SlipCase {
        return SlipCase(
            reasonId = this.slipReasonId,
            slipDateMillis = this.slipDateMillis,
        )
    }

    private fun SlipCase.toEntity(): SlipCaseEntity {
        return SlipCaseEntity(
            slipReasonId = this.reasonId,
            slipDateMillis = this.slipDateMillis,
        )
    }
}

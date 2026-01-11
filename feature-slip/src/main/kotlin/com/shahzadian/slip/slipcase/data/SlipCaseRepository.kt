package com.shahzadian.slip.slipcase.data

import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.slip.slipcase.db.SlipCaseDatabase
import com.shahzadian.slip.slipcase.db.SlipCaseEntity
import com.shahzadian.slip.slipcase.domain.SlipCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface SlipCaseRepository {

    suspend fun getSlipCases(): List<SlipCase>
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

    private fun SlipCaseEntity.toDomain(): SlipCase {
        return SlipCase(
            slipId = this.slipId,
            slipDateMillis = this.slipDateMillis,
        )
    }
}

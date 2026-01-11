package com.shahzadian.slip.slipcase.data

import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.slip.slipcase.domain.SlipCase
import javax.inject.Inject

internal interface SlipCaseRepository {

    suspend fun getSlipCases(): List<SlipCase>
}

internal class SlipCaseRepositoryImpl @Inject constructor(
    private val coroutineContexts: CoroutineContexts,
) : SlipCaseRepository {

    override suspend fun getSlipCases(): List<SlipCase> {
        TODO("Not yet implemented")
    }
}
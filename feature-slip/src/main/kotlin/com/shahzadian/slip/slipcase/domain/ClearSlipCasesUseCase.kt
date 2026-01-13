package com.shahzadian.slip.slipcase.domain

import com.shahzadian.slip.slipcase.data.SlipCaseRepository
import javax.inject.Inject

interface ClearSlipCasesUseCase {

    suspend operator fun invoke()
}

internal class ClearSlipCasesUseCaseImpl @Inject constructor(
    private val slipCaseRepository: SlipCaseRepository
) : ClearSlipCasesUseCase {

    override suspend fun invoke() {
        slipCaseRepository.clearAll()
    }
}
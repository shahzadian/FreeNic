package com.shahzadian.slip.slipcase.domain

import com.shahzadian.slip.slipcase.data.SlipCaseRepository
import javax.inject.Inject

interface GetSlipCasesUseCase {

    suspend operator fun invoke(): List<SlipCase>
}

internal class GetSlipCasesUseCaseImpl @Inject constructor(
    private val slipCaseRepository: SlipCaseRepository
) : GetSlipCasesUseCase {

    override suspend fun invoke(): List<SlipCase> {
        return slipCaseRepository.getSlipCases()
    }
}

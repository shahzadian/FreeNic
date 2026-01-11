package com.shahzadian.slip.slipcase.domain

import com.shahzadian.slip.slipcase.data.SlipCaseRepository
import javax.inject.Inject

interface CreateSlipCaseUseCase {

    suspend operator fun invoke(slipCase: SlipCase)
}

internal class CreateSlipCaseUseCaseImpl @Inject constructor(
    private val slipCaseRepository: SlipCaseRepository
) : CreateSlipCaseUseCase {

    override suspend fun invoke(slipCase: SlipCase) {
        slipCaseRepository.insertSlipCase(slipCase)
    }
}
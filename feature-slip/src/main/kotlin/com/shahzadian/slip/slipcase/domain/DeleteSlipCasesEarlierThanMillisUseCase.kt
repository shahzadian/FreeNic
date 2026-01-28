package com.shahzadian.slip.slipcase.domain

import com.shahzadian.slip.slipcase.data.SlipCaseRepository
import javax.inject.Inject

interface DeleteSlipCasesEarlierThanMillisUseCase {

    suspend operator fun invoke(millis: Long)
}

internal class DeleteSlipCasesEarlierThanMillisUseCaseImpl @Inject constructor(
    private val slipCaseRepository: SlipCaseRepository
) : DeleteSlipCasesEarlierThanMillisUseCase {

    override suspend fun invoke(millis: Long) {
        slipCaseRepository.deleteSlipCasesEarlierThanMillis(millis)
    }
}
package com.shahzadian.slip.slipreason.domain

import com.shahzadian.slip.slipreason.data.SlipReasonRepository
import javax.inject.Inject

interface GetSlipReasonsUseCase {

    suspend operator fun invoke(): List<SlipReason>
}

internal class GetSlipReasonsUseCaseImpl @Inject constructor(
    private val slipReasonRepository: SlipReasonRepository
) : GetSlipReasonsUseCase {

    override suspend fun invoke(): List<SlipReason> {
        return slipReasonRepository.getSlipReasons()
    }
}

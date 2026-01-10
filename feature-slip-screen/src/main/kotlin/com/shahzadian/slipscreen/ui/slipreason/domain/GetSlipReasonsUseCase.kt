package com.shahzadian.slipscreen.ui.slipreason.domain

import com.shahzadian.slipscreen.ui.slipreason.data.SlipReasonRepository
import javax.inject.Inject

internal interface GetSlipReasonsUseCase {

    suspend operator fun invoke(): List<SlipReason>
}

internal class GetSlipReasonsUseCaseImpl @Inject constructor(
    private val slipReasonRepository: SlipReasonRepository
) : GetSlipReasonsUseCase {

    override suspend fun invoke(): List<SlipReason> {
        return slipReasonRepository.getSlipReasons()
    }
}

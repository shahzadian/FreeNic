package com.shahzadian.slipscreen.ui.slipcontext.domain

import com.shahzadian.slipscreen.ui.slipcontext.data.SlipContextRepository
import javax.inject.Inject

internal interface GetSlipContextsUseCase {

    suspend operator fun invoke(): List<SlipContext>
}

internal class GetSlipContextsUseCaseImpl @Inject constructor(
    private val slipContextRepository: SlipContextRepository
) : GetSlipContextsUseCase {

    override suspend fun invoke(): List<SlipContext> {
        return slipContextRepository.getSlipContexts()
    }
}

package com.shahzadian.slipscreen.ui.slipcontext.data

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.slipscreen.ui.slipcontext.domain.SlipContext
import javax.inject.Inject

internal interface SlipContextRepository {

    suspend fun getSlipContexts(): List<SlipContext>
}

internal class SlipContextRepositoryImpl @Inject constructor() : SlipContextRepository {

    override suspend fun getSlipContexts(): List<SlipContext> {
        return listOf(
            SlipContext(
                id = "Stress",
                name = TextWrapper.FromRes(R.string.slip_context_stress),
                icon = ImageWrapper.FromRes(com.shahzadian.slipscreen.R.drawable.ic_slip_context_stress)
            ),
            SlipContext(
                id = "Social",
                name = TextWrapper.FromRes(R.string.slip_context_social),
                icon = ImageWrapper.FromRes(com.shahzadian.slipscreen.R.drawable.ic_slip_context_social),
            ),
            SlipContext(
                id = "Habit",
                name = TextWrapper.FromRes(R.string.slip_context_habit),
                icon = ImageWrapper.FromRes(com.shahzadian.slipscreen.R.drawable.ic_slip_context_habit),
            ),
            SlipContext(
                id = "Craving",
                name = TextWrapper.FromRes(R.string.slip_context_craving),
                icon = ImageWrapper.FromRes(com.shahzadian.slipscreen.R.drawable.ic_slip_context_craving),
            ),
            SlipContext(
                id = "Other",
                name = TextWrapper.FromRes(R.string.slip_context_other),
                icon = ImageWrapper.FromRes(com.shahzadian.slipscreen.R.drawable.ic_slip_context_other),
            ),
        )
    }
}

package com.shahzadian.slip.slipreason.data

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.slip.R
import com.shahzadian.slip.slipreason.domain.SlipReason
import javax.inject.Inject

internal interface SlipReasonRepository {

    suspend fun getSlipReasons(): List<SlipReason>
}

internal class SlipReasonRepositoryImpl @Inject constructor() : SlipReasonRepository {

    override suspend fun getSlipReasons(): List<SlipReason> {
        return listOf(
            SlipReason(
                id = "Stress",
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.slip_context_stress),
                icon = ImageWrapper.FromRes(R.drawable.ic_slip_reason_stress)
            ),
            SlipReason(
                id = "Social",
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.slip_reason_social),
                icon = ImageWrapper.FromRes(R.drawable.ic_slip_reason_social),
            ),
            SlipReason(
                id = "Habit",
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.slip_reason_habit),
                icon = ImageWrapper.FromRes(R.drawable.ic_slip_reason_habit),
            ),
            SlipReason(
                id = "Craving",
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.slip_reason_craving),
                icon = ImageWrapper.FromRes(R.drawable.ic_slip_reason_craving),
            ),
            SlipReason(
                id = "Other",
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.slip_reason_other),
                icon = ImageWrapper.FromRes(R.drawable.ic_slip_reason_other),
            ),
        )
    }
}

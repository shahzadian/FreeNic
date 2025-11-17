package com.shahzadian.datafill.domain.fillableInfo.aboutinfo

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent

internal data class AboutScreenFillableInfo(
    val name: String?,
    val age: Int?,
) : FillableInfo

internal sealed interface AboutScreenFillableInfoEvent : FillableInfoEvent {
    data class OnNameChanged(val name: String?) : AboutScreenFillableInfoEvent
    data class OnAgeChanged(val age: Int?) : AboutScreenFillableInfoEvent
}

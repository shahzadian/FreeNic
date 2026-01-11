package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class DayCellUiModel(
    val dayText: TextWrapper?,
    val inCurrentMonth: Boolean,
    val isToday: Boolean,
    val events: List<DayEventUiModel>,
)

internal sealed interface DayEventUiModel {

    val icon: ImageWrapper
}
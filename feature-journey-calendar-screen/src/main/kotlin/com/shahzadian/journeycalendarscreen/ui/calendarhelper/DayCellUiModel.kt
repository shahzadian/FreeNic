package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class DayCellUiModel(
    val calendarDayText: TextWrapper?,
    val fullDayText: TextWrapper?,
    val isInCurrentMonth: Boolean,
    val isInSmokeQuitPeriod: Boolean,
    val isToday: Boolean,
    val events: List<DayEventUiModel>,
)


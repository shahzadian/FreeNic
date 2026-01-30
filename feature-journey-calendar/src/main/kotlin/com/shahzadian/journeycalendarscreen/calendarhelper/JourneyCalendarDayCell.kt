package com.shahzadian.journeycalendarscreen.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.TextWrapper

@Immutable
data class JourneyCalendarDayCell(
    val calendarDayText: TextWrapper?,
    val fullDayText: TextWrapper?,
    val isInCurrentMonth: Boolean,
    val isInSmokeQuitPeriod: Boolean,
    val isToday: Boolean,
    val isInFuture: Boolean,
    val events: List<JourneyCalendarDayEvent>,
    val maxDisplayableEvents: Int,
)

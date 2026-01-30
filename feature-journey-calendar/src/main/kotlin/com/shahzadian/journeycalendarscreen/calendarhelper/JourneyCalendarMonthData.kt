package com.shahzadian.journeycalendarscreen.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.TextWrapper

@Immutable
data class JourneyCalendarMonthData(
    val monthTitle: TextWrapper,
    val weeks: List<List<JourneyCalendarDayCell>>,
)

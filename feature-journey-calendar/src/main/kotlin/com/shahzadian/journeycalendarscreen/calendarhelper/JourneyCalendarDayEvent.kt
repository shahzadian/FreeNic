package com.shahzadian.journeycalendarscreen.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
data class JourneyCalendarDayEvent(
    val title: TextWrapper,
    val subtitle: TextWrapper?,
    val icon: ImageWrapper,
    val showTypes: List<DayEventShowType> = listOf(
        DayEventShowType.BottomSheet,
        DayEventShowType.Preview,
    )
)

sealed interface DayEventShowType {
    data object Preview : DayEventShowType
    data object BottomSheet : DayEventShowType
}
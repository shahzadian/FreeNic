package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayCell

@Immutable
internal sealed interface JourneyCalendarViewEvent : ViewEvent {

    @Immutable
    data object OnPreviousMonthClicked : JourneyCalendarViewEvent

    @Immutable
    data object OnNextMonthClicked : JourneyCalendarViewEvent

    @Immutable
    data object OnBackIconClicked : JourneyCalendarViewEvent

    @Immutable
    data class OnDayTapped(val day: JourneyCalendarDayCell) : JourneyCalendarViewEvent

    @Immutable
    data object OnBottomSheetDismissRequired : JourneyCalendarViewEvent

    @Immutable
    data object OnCalendarPreviewSubscribeClicked : JourneyCalendarViewEvent

    @Immutable
    data object OnWatchVideoClicked : JourneyCalendarViewEvent
}

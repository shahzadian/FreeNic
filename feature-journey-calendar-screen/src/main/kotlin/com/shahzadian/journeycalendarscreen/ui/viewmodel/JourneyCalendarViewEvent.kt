package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayCellUiModel

@Immutable
internal sealed interface JourneyCalendarViewEvent : ViewEvent {

    @Immutable
    data object OnPreviousMonthClicked : JourneyCalendarViewEvent

    @Immutable
    data object OnNextMonthClicked : JourneyCalendarViewEvent

    @Immutable
    data object OnBackIconClicked : JourneyCalendarViewEvent

    @Immutable
    data class OnDayTapped(val day: DayCellUiModel) : JourneyCalendarViewEvent

    @Immutable
    data object OnBottomSheetDismissRequired : JourneyCalendarViewEvent

    @Immutable
    data object OnAddNoteClicked : JourneyCalendarViewEvent

    @Immutable
    data object OnAddNoteSheetDismissRequired : JourneyCalendarViewEvent

    @Immutable
    data object OnAddNoteUnlockClicked : JourneyCalendarViewEvent
}

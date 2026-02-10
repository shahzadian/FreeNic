package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface JourneyCalendarViewCommand : ViewCommand {

    @Immutable
    data object NavigateBack : JourneyCalendarViewCommand

    @Immutable
    data class NavigateTo(val screen: AppScreen) : JourneyCalendarViewCommand

    data class NavigateToPaywallScreen(
        val source: String,
    ) : JourneyCalendarViewCommand

    @Immutable
    data class ShowError(val message: TextWrapper) : JourneyCalendarViewCommand
}

package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface JourneyCalendarViewCommand : ViewCommand {

    @Immutable
    data object NavigateBack : JourneyCalendarViewCommand
}

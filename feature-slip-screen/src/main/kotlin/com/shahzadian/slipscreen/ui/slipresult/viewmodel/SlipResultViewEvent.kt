package com.shahzadian.slipscreen.ui.slipresult.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface SlipResultViewEvent : ViewEvent {

    @Immutable
    data object OnReadMotivationalNotesClicked : SlipResultViewEvent

    @Immutable
    data object OnBackToDashboardClicked : SlipResultViewEvent
}

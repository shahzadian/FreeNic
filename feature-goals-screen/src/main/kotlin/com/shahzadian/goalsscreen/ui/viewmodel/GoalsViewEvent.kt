package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface GoalsViewEvent : ViewEvent {

    @Immutable
    data object OnSavingGoalsUpgradeClicked : GoalsViewEvent
}

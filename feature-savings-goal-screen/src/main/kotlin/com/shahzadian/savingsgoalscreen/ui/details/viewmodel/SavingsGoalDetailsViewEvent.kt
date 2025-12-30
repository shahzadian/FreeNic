package com.shahzadian.savingsgoalscreen.ui.details.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface SavingsGoalDetailsViewEvent : ViewEvent {
    data object OnDeleteClicked : SavingsGoalDetailsViewEvent
}

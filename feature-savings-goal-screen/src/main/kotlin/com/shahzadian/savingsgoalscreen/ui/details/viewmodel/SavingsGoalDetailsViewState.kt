package com.shahzadian.savingsgoalscreen.ui.details.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface SavingsGoalDetailsViewState : ViewState {

    @Immutable
    data object Loading : SavingsGoalDetailsViewState

    data object Data : SavingsGoalDetailsViewState
}

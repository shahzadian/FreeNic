package com.shahzadian.savingsgoalscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface SavingsGoalCreateViewState : ViewState {

    data object Loading : SavingsGoalCreateViewState

    @Immutable
    data class Data(
        val goalName: String?,
        val goalAmount: String?,
    ): SavingsGoalCreateViewState
}

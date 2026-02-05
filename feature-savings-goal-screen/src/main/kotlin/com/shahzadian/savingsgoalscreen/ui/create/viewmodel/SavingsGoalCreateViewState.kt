package com.shahzadian.savingsgoalscreen.ui.create.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface SavingsGoalCreateViewState : ViewState {

    data object Loading : SavingsGoalCreateViewState

    data class Data(
        val model: SavingsGoalCreateDataUiModel,
    ) : SavingsGoalCreateViewState
}

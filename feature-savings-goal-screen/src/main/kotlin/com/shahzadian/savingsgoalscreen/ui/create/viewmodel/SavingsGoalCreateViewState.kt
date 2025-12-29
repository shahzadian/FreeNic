package com.shahzadian.savingsgoalscreen.ui.create.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalDataUiModel

@Immutable
internal sealed interface SavingsGoalCreateViewState : ViewState {

    data object Loading : SavingsGoalCreateViewState

    data class Data(
        val model: SavingsGoalDataUiModel,
    ) : SavingsGoalCreateViewState

}

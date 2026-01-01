package com.shahzadian.savingsgoalscreen.ui.share.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalDataUiModel

@Immutable
internal sealed interface SavingsGoalShareViewState : ViewState {

    data object Loading : SavingsGoalShareViewState

    data class Data(val dataUiModel: SavingsGoalDataUiModel) : SavingsGoalShareViewState
}

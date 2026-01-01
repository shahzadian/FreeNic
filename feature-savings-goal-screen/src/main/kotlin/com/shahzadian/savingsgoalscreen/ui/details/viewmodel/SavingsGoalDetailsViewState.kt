package com.shahzadian.savingsgoalscreen.ui.details.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalDataUiModel

@Immutable
internal sealed interface SavingsGoalDetailsViewState : ViewState {

    @Immutable
    data object Loading : SavingsGoalDetailsViewState

    @Immutable
    data class Data(
        val uiModel: SavingsGoalDataUiModel,
    ) : SavingsGoalDetailsViewState

}

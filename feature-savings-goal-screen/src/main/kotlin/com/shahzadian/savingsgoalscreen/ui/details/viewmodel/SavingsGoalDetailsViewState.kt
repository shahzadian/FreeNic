package com.shahzadian.savingsgoalscreen.ui.details.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalCategoryUiModel

@Immutable
internal sealed interface SavingsGoalDetailsViewState : ViewState {

    @Immutable
    data object Loading : SavingsGoalDetailsViewState

    @Immutable
    data class Data(
        val uiModel: SavingsGoalDataUiModel,
    ) : SavingsGoalDetailsViewState

    @Immutable
    data class SavingsGoalDataUiModel(
        val name: String,
        val necessaryAmount: Float,
        val currentAmount: Float,
        val noCigDays: Int,
        val currency: String?,
        val achievedPercents: Int,
        val daysLeft: Int,
        val amountLeft: Float,
        val category: SavingsGoalCategoryUiModel,
    )
}

package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface GoalsViewState : ViewState {

    @Immutable
    data object Loading : GoalsViewState

    @Immutable
    data class Data(
        val hasPaidSubscription: Boolean,
        val achievementsData: AchievementsData,
        val savingsGoal: SavingsGoalUiModel?,
    ) : GoalsViewState
    data class SavingsGoalUiModel(
        val name: String,
        val necessaryAmount: Float,
        val currentAmount: Float,
        val achievedPercents: Float,
        val category: TextWrapper,
    )
}

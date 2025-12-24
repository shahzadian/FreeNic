package com.shahzadian.savingsgoalscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface SavingsGoalCreateViewState : ViewState {

    data object Loading : SavingsGoalCreateViewState

    @Immutable
    data class Data(
        val goalName: String?,
        val goalAmount: String?,
        val categories: List<SavingsGoalCategoryUiModel>,
        val selectedCategoryId: String?,
        val isSubmitButtonEnabled: Boolean,
    ): SavingsGoalCreateViewState

    data class SavingsGoalCategoryUiModel(
        val id: String,
        val name: TextWrapper,
        val icon: ImageWrapper,
    )
}

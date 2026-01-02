package com.shahzadian.savingsgoalscreen.ui.create.viewmodel

import androidx.compose.runtime.Immutable
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalCategoryUiModel

@Immutable
data class SavingsGoalCreateDataUiModel(
    val goalName: String?,
    val goalAmount: String?,
    val currentAmount: String?,
    val categories: List<SavingsGoalCategoryUiModel>,
    val selectedCategoryId: String?,
    val isSubmitButtonEnabled: Boolean,
)

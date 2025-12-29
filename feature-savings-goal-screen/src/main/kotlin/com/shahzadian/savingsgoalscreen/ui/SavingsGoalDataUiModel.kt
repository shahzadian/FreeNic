package com.shahzadian.savingsgoalscreen.ui

import androidx.compose.runtime.Immutable

@Immutable
data class SavingsGoalDataUiModel(
    val goalName: String?,
    val goalAmount: String?,
    val categories: List<SavingsGoalCategoryUiModel>,
    val selectedCategoryId: String?,
    val isSubmitButtonEnabled: Boolean,
)

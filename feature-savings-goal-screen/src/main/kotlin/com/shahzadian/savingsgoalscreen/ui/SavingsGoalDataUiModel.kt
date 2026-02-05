package com.shahzadian.savingsgoalscreen.ui

import androidx.compose.runtime.Immutable

@Immutable
internal data class SavingsGoalDataUiModel(
    val name: String,
    val necessaryAmount: String,
    val currentAmount: String,
    val noCigDays: Int,
    val achievedPercents: Int,
    val daysLeft: Int,
    val amountLeft: String,
    val category: SavingsGoalCategoryUiModel,
)
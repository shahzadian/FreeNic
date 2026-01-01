package com.shahzadian.savingsgoalscreen.ui

import androidx.compose.runtime.Immutable

@Immutable
internal data class SavingsGoalDataUiModel(
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
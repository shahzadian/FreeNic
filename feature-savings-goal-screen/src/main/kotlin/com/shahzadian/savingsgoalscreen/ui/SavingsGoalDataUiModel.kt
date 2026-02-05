package com.shahzadian.savingsgoalscreen.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class SavingsGoalDataUiModel(
    val name: String,
    val necessaryAmount: TextWrapper,
    val currentAmount: TextWrapper,
    val noCigDays: Int,
    val achievedPercents: Int,
    val daysLeft: TextWrapper,
    val amountLeft: TextWrapper,
    val category: SavingsGoalCategoryUiModel,
)
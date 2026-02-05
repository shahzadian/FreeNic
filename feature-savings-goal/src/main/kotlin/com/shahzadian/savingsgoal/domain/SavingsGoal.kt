package com.shahzadian.savingsgoal.domain

import com.freenic.ui.wrapper.TextWrapper

class SavingsGoal(
    val name: String,
    val necessaryAmount: Float,
    val displayableNecessaryAmount: TextWrapper,
    val currentAmount: Float,
    val displayableCurrentAmount: TextWrapper,
    val currency: String?,
    val noCigDays: Int,
    val achievedPercents: Float,
    val createTimeMillis: Long,
    val daysLeft: Int,
    val displayableDaysLeft: TextWrapper,
    val amountLeft: Float,
    val displayableAmountLeft: TextWrapper,
    val categoryId: String,
)

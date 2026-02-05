package com.shahzadian.savingsgoal.domain

class SavingsGoal(
    val name: String,
    val necessaryAmount: Float,
    val displayableNecessaryAmount: String,
    val currentAmount: Float,
    val displayableCurrentAmount: String,
    val currency: String?,
    val noCigDays: Int,
    val achievedPercents: Float,
    val createTimeMillis: Long,
    val daysLeft: Int,
    val amountLeft: Float,
    val displayableAmountLeft: String,
    val categoryId: String,
)

package com.shahzadian.savingsgoal.domain

class SavingsGoal(
    val name: String,
    val necessaryAmount: Float,
    val currentAmount: Float,
    val currency: String?,
    val noCigDays: Int,
    val achievedPercents: Float,
    val daysLeft: Int,
    val amountLeft: Float,
    val categoryId: String,
)

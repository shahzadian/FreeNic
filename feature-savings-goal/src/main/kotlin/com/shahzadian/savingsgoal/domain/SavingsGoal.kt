package com.shahzadian.savingsgoal.domain

class SavingsGoal(
    val name: String,
    val necessaryAmount: Float,
    val currentAmount: Float,
    val achievedPercents: Float,
    val categoryId: String,
)

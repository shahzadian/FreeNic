package com.shahzadian.savingsgoal.data

class SavingsGoalData(
    val name: String,
    val necessaryAmount: Float,
    // Initial amount user has already saved at the moment of goal creation
    val currentAmount: Float,
    val createTimeMillis: Long,
    val categoryId: String,
)

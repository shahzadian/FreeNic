package com.shahzadian.savingsgoal.domain

import com.shahzadian.savingsgoal.data.SavingsGoalRepository
import javax.inject.Inject

interface SavingsGoalHelper {

    suspend fun getCurrentSavingsGoal(): SavingsGoal?

    suspend fun onSavingsGoalChanged(savingsGoal: SavingsGoal)
}

internal class SavingsGoalHelperImpl @Inject constructor(
    private val savingsGoalRepository: SavingsGoalRepository
) : SavingsGoalHelper {

    override suspend fun getCurrentSavingsGoal(): SavingsGoal? {
        return savingsGoalRepository.getActiveSavingsGoal()
    }

    override suspend fun onSavingsGoalChanged(savingsGoal: SavingsGoal) {
        savingsGoalRepository.onSavingsGoalsChanged(savingsGoal)
    }
}
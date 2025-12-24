package com.shahzadian.savingsgoal.domain

import com.shahzadian.savingsgoal.data.SavingsGoalRepository
import com.shahzadian.savingsgoal.data.category.SavingsGoalCategoryRepository
import javax.inject.Inject

interface SavingsGoalHelper {

    suspend fun getCurrentSavingsGoal(): SavingsGoal?

    suspend fun getAllCategories(): List<SavingsGoalCategory>

    suspend fun onSavingsGoalChanged(savingsGoal: SavingsGoal)
}

internal class SavingsGoalHelperImpl @Inject constructor(
    private val savingsGoalRepository: SavingsGoalRepository,
    private val savingsGoalCategoryRepository: SavingsGoalCategoryRepository,
) : SavingsGoalHelper {

    override suspend fun getCurrentSavingsGoal(): SavingsGoal? {
        return savingsGoalRepository.getActiveSavingsGoal()
    }

    override suspend fun getAllCategories(): List<SavingsGoalCategory> {
        return savingsGoalCategoryRepository.getSavingsGoalCategories()
    }

    override suspend fun onSavingsGoalChanged(savingsGoal: SavingsGoal) {
        savingsGoalRepository.onSavingsGoalsChanged(savingsGoal)
    }
}
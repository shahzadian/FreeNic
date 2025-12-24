package com.shahzadian.savingsgoal.domain

import com.shahzadian.savingsgoal.data.SavingsGoalRepository
import com.shahzadian.savingsgoal.data.category.SavingsGoalCategoryRepository
import com.shahzadian.savingsgoal.mapper.SavingsGoalMapper
import javax.inject.Inject

interface SavingsGoalHelper {

    suspend fun getCurrentSavingsGoal(): SavingsGoal?

    suspend fun getAllCategories(): List<SavingsGoalCategory>

    suspend fun createSavingsGoal(
        name: String,
        amount: Double,
        categoryId: String,
    )

    suspend fun onSavingsGoalChanged(savingsGoal: SavingsGoal)
}

internal class SavingsGoalHelperImpl @Inject constructor(
    private val savingsGoalRepository: SavingsGoalRepository,
    private val savingsGoalCategoryRepository: SavingsGoalCategoryRepository,
    private val savingsGoalMapper: SavingsGoalMapper,
) : SavingsGoalHelper {

    override suspend fun getCurrentSavingsGoal(): SavingsGoal? {
        return savingsGoalRepository
            .getActiveSavingsGoal()
            ?.let { savingsGoalMapper.fromDataToDomain(it) }
    }

    override suspend fun getAllCategories(): List<SavingsGoalCategory> {
        return savingsGoalCategoryRepository.getSavingsGoalCategories()
    }

    override suspend fun createSavingsGoal(
        name: String,
        amount: Double,
        categoryId: String
    ) {
        savingsGoalMapper.createDomain(name, amount, categoryId)
    }

    override suspend fun onSavingsGoalChanged(savingsGoal: SavingsGoal) {
        savingsGoalRepository.onSavingsGoalsChanged(savingsGoalMapper.fromDomainToData(savingsGoal))
    }
}

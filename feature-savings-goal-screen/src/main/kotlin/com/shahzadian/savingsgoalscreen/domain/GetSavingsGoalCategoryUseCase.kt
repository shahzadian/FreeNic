package com.shahzadian.savingsgoalscreen.domain

import com.shahzadian.savingsgoalscreen.data.SavingsGoalCategoryRepository
import javax.inject.Inject

internal interface GetSavingsGoalCategoryUseCase {

    suspend operator fun invoke(): List<SavingsGoalCategory>
}

internal class GetSavingsGoalCategoryUseCaseImpl @Inject constructor(
    private val savingsGoalCategoryRepository: SavingsGoalCategoryRepository
) : GetSavingsGoalCategoryUseCase {

    override suspend fun invoke(): List<SavingsGoalCategory> {
        return savingsGoalCategoryRepository.getSavingsGoalCategories()
    }
}

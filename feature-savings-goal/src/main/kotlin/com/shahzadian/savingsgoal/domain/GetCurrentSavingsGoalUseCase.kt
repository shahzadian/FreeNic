package com.shahzadian.savingsgoal.domain

import javax.inject.Inject

interface GetCurrentSavingsGoalUseCase {

    suspend operator fun invoke(): SavingsGoal?
}

internal class GetCurrentSavingsGoalUseCaseImpl @Inject constructor(
    private val savingsGoalHelper: SavingsGoalHelper,
) : GetCurrentSavingsGoalUseCase {

    override suspend fun invoke(): SavingsGoal? {
        return savingsGoalHelper.getCurrentSavingsGoal()
    }
}
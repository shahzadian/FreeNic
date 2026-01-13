package com.shahzadian.savingsgoal.domain

import com.shahzadian.savingsgoal.data.SavingsGoalRepository
import javax.inject.Inject

interface ClearSavingsGoalUseCase {

    suspend operator fun invoke()
}

internal class ClearSavingsGoalUseCaseImpl @Inject constructor(
    private val savingsGoalRepository: SavingsGoalRepository,
) : ClearSavingsGoalUseCase {

    override suspend fun invoke() {
        savingsGoalRepository.deleteSavingsGoal()
    }
}

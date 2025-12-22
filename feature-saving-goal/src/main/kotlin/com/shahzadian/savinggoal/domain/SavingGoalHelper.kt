package com.shahzadian.savinggoal.domain

import com.shahzadian.savinggoal.data.SavingGoalRepository
import javax.inject.Inject

interface SavingGoalHelper {

    suspend fun getCurrentSavingGoal(): SavingGoal?

    suspend fun onSavingGoalChanged(savingGoal: SavingGoal)
}

internal class SavingGoalHelperImpl @Inject constructor(
    private val savingGoalRepository: SavingGoalRepository
) : SavingGoalHelper {

    override suspend fun getCurrentSavingGoal(): SavingGoal? {
        return savingGoalRepository.getSavingGoal()
    }

    override suspend fun onSavingGoalChanged(savingGoal: SavingGoal) {
        savingGoalRepository.onSavingGoalChanged(savingGoal)
    }
}
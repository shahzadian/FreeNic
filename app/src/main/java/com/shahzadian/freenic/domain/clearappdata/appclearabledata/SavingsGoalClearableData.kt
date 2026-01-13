package com.shahzadian.freenic.domain.clearappdata.appclearabledata

import com.shahzadian.savingsgoal.domain.ClearSavingsGoalUseCase

internal class SavingsGoalClearableData(
    private val clearSavingsGoalUseCase: ClearSavingsGoalUseCase
) : AppClearableData() {

    override suspend fun clearData() {
        clearSavingsGoalUseCase()
    }
}

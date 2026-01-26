package com.shahzadian.freenic.domain.clearappdata.appclearabledata

import android.content.Context
import com.shahzadian.savingsgoal.domain.ClearSavingsGoalUseCase
import com.shahzadian.slip.slipcase.domain.ClearSlipCasesUseCase
import com.shahzadian.user.domain.getuserdata.ClearUserDataUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal interface AppClearableDataFactory {

    fun create(): List<AppClearableData>
}

internal class AppClearableDataFactoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val clearUserDataUseCase: ClearUserDataUseCase,
    private val clearSlipCasesUseCase: ClearSlipCasesUseCase,
    private val clearSavingsGoalUseCase: ClearSavingsGoalUseCase,
) : AppClearableDataFactory {

    override fun create(): List<AppClearableData> {
        return listOf(
            UserClearableData(
                context = context,
                clearUserDataUseCase = clearUserDataUseCase,
            ),
            SlipCasesClearableData(
                clearSlipCasesUseCase = clearSlipCasesUseCase,
            ),
            SavingsGoalClearableData(
                clearSavingsGoalUseCase = clearSavingsGoalUseCase,
            )
        )
    }
}

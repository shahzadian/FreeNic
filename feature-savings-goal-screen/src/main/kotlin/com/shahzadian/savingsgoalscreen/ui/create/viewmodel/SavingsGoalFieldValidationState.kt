package com.shahzadian.savingsgoalscreen.ui.create.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface SavingsGoalFieldValidationState {

    @Immutable
    data object Valid : SavingsGoalFieldValidationState

    @Immutable
    data class Invalid(
        val errorMessage: TextWrapper,
    ) : SavingsGoalFieldValidationState
}
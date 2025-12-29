package com.shahzadian.savingsgoalscreen.ui.create.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface SavingsGoalCreateViewEvent : ViewEvent {

    @Immutable
    data class OnGoalNameChanged(val name: String) : SavingsGoalCreateViewEvent

    @Immutable
    data class OnAmountChanged(val amount: String) : SavingsGoalCreateViewEvent

    @Immutable
    data class OnCategoryChanged(val categoryId: String) : SavingsGoalCreateViewEvent

    data object OnSubmitClicked : SavingsGoalCreateViewEvent
}

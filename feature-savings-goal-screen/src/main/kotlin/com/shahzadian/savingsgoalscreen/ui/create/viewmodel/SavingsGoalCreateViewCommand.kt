package com.shahzadian.savingsgoalscreen.ui.create.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface SavingsGoalCreateViewCommand : ViewCommand {

    data object NavigateBack : SavingsGoalCreateViewCommand
}

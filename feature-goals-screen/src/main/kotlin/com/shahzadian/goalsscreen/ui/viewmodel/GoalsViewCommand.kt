package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface GoalsViewCommand : ViewCommand {

    @Immutable
    data class NavigateTo(val appScreen: AppScreen) : GoalsViewCommand
}

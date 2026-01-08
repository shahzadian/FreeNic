package com.shahzadian.dashboard.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface DashboardViewCommand : ViewCommand {

    @Immutable
    data class NavigateTo(val screen: AppScreen) : DashboardViewCommand
}

package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface InsightsViewCommand : ViewCommand {

    @Immutable
    data class NavigateTo(val screen: AppScreen): InsightsViewCommand

    @Immutable
    data object NavigateBack : InsightsViewCommand
}

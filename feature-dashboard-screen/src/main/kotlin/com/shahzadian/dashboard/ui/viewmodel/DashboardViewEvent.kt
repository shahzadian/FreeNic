package com.shahzadian.dashboard.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface DashboardViewEvent : ViewEvent {

    @Immutable
    data object OnCravingsHelpClicked : DashboardViewEvent

    @Immutable
    data object OnDetailedInsightsClicked : DashboardViewEvent
}

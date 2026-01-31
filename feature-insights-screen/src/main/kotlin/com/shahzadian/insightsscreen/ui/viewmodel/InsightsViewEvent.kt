package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface InsightsViewEvent : ViewEvent {

    @Immutable
    data object OnSubscribeClicked : InsightsViewEvent

    @Immutable
    data object OnBackIconClicked : InsightsViewEvent

    @Immutable
    data object OnWatchVideoClicked : InsightsViewEvent
}
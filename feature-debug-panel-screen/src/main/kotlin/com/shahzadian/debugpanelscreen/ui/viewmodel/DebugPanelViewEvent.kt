package com.shahzadian.debugpanelscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface DebugPanelViewEvent : ViewEvent {

    data class OnHasPaidSubscriptionSwitched(
        val isSwitchOn: Boolean,
    ): DebugPanelViewEvent
}

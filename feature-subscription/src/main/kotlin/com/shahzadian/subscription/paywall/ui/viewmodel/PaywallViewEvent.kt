package com.shahzadian.subscription.paywall.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface PaywallViewEvent : ViewEvent {

    @Immutable
    data object OnBackIconClicked : PaywallViewEvent
}

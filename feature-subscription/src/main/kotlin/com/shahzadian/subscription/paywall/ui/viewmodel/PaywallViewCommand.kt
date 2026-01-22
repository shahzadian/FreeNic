package com.shahzadian.subscription.paywall.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface PaywallViewCommand : ViewCommand {

    @Immutable
    data object NavigateBack : PaywallViewCommand

    @Immutable
    data class NavigateTo(
        val screen: AppScreen,
        val keepInStack: Boolean = true,
    ) : PaywallViewCommand
}

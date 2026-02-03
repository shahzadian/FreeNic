package com.shahzadian.profilescreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface ProfileViewCommand : ViewCommand {

    @Immutable
    data class NavigateTo(
        val screen: AppScreen,
        val clearFromStack: Boolean = false,
    ) : ProfileViewCommand

    data class NavigateToPaywallScreen(
        val source: String,
    ) : ProfileViewCommand
}

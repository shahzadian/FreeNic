package com.shahzadian.welcomescreen.ui.welcomescreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
sealed interface WelcomeViewCommand : ViewCommand {

    data class Navigate(
        val screen: AppScreen,
        val keepInStack: Boolean = true,
    ) : WelcomeViewCommand
}

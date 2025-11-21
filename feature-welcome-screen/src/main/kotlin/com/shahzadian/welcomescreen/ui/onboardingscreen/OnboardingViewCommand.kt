package com.shahzadian.welcomescreen.ui.onboardingscreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface OnboardingViewCommand : ViewCommand {

    @Immutable
    data class NavigateTo(val screen: AppScreen) : OnboardingViewCommand
}

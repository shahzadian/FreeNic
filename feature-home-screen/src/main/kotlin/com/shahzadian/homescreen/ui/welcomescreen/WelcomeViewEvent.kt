package com.shahzadian.homescreen.ui.welcomescreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
sealed interface WelcomeViewEvent : ViewEvent {

    data object OnGetStartedClicked : WelcomeViewEvent
}

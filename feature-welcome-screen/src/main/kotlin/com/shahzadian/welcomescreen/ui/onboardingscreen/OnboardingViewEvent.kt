package com.shahzadian.welcomescreen.ui.onboardingscreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
sealed interface OnboardingViewEvent : ViewEvent {

    data object OnSubmitButtonClicked : OnboardingViewEvent
}

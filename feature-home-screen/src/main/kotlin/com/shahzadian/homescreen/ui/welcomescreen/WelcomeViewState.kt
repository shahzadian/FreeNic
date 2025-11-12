package com.shahzadian.homescreen.ui.welcomescreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
sealed interface WelcomeViewState : ViewState {
    data object Default : WelcomeViewState
}

package com.shahzadian.homescreen.ui.welcomescreen

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import kotlinx.coroutines.launch

class WelcomeViewModel : BaseViewModel<WelcomeViewState, WelcomeViewEvent, WelcomeViewCommand>() {

    override val initState: WelcomeViewState by lazyUnsafe {
        WelcomeViewState.Data(
            title = TextWrapper.FromRes(
                resId = R.string.welcome_screen_title,
            ),
            description = TextWrapper.FromRes(
                resId = R.string.welcome_screen_desc,
            ),
            buttonText = TextWrapper.FromRes(
                resId = R.string.welcome_screen_button,
            )
        )
    }

    override fun onViewEventHappened(event: WelcomeViewEvent) {
        when (event) {
            is WelcomeViewEvent.OnGetStartedClicked -> {
                viewModelScope.launch {
                    sendViewCommand(
                        WelcomeViewCommand.Navigate(
                            screen = AppScreen.OnboardingScreen
                        )
                    )
                }
            }
        }
    }
}

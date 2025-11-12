package com.shahzadian.homescreen.ui.welcomescreen

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WelcomeViewModel @Inject constructor(
) : BaseViewModel<WelcomeViewState, WelcomeViewEvent, WelcomeViewCommand>() {

    override val initState: WelcomeViewState by lazyUnsafe {
        WelcomeViewState.Default
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

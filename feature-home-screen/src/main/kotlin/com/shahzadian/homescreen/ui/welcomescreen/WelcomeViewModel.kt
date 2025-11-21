package com.shahzadian.homescreen.ui.welcomescreen

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WelcomeViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
) : BaseViewModel<WelcomeViewState, WelcomeViewEvent, WelcomeViewCommand>() {

    override val initState: WelcomeViewState by lazyUnsafe {
        WelcomeViewState.Loading
    }

    init {
        viewModelScope.launch {
            val isUserRegistered = getUserDataUseCase.isUserRegistered()
            if (isUserRegistered) {
                sendViewCommand(
                    WelcomeViewCommand.Navigate(
                        screen = AppScreen.DashboardScreen
                    )
                )
            } else {
                setViewState(
                    WelcomeViewState.GetStarted
                )
            }
        }
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

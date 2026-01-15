package com.shahzadian.welcomescreen.ui.welcomescreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WelcomeViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<WelcomeViewState, WelcomeViewEvent, WelcomeViewCommand>() {

    override val initState: WelcomeViewState by lazyUnsafe {
        WelcomeViewState.Loading
    }

    private val targetScreen: String? by lazyUnsafe {
        savedStateHandle.get<String>(AppScreen.WelcomeScreen.ARG_TARGET_SCREEN)
    }

    init {
        viewModelScope.launch {
            val isUserRegistered = getUserDataUseCase.isUserRegistered()
            if (isUserRegistered) {
                val screenToNavigate = targetScreen
                    ?.let { screen ->
                        AppScreen.getAll().firstOrNull {
                            it.getBaseRoute() == screen
                        }
                    }

                if (screenToNavigate == null) {
                    sendViewCommand(
                        WelcomeViewCommand.Navigate(
                            screen = AppScreen.MainScreen,
                            keepInStack = false,
                        )
                    )
                } else {
//                    Main screen is added to stack first
                    if (screenToNavigate != AppScreen.MainScreen) {
                        sendViewCommand(
                            WelcomeViewCommand.Navigate(
                                screen = AppScreen.MainScreen,
                                keepInStack = false,
                            )
                        )
                    }
                    delay(500)
                    sendViewCommand(
                        WelcomeViewCommand.Navigate(
                            screen = screenToNavigate
                        )
                    )
                }
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
                    analyticsEventSender.send(
                        AnalyticsEvent(
                            name = AnalyticsEventConst.REGISTRATION_START,
                        )
                    )
                    sendViewCommand(
                        WelcomeViewCommand.Navigate(
                            screen = AppScreen.DataFillScreen
                        )
                    )
                }
            }
        }
    }
}

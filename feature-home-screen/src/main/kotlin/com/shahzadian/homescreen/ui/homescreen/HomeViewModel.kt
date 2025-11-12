package com.shahzadian.homescreen.ui.homescreen

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel<HomeViewState, HomeViewEvent, HomeViewCommand>() {

    override val initState: HomeViewState by lazy {
        HomeViewState(
            isLoading = false,
        )
    }

    init {
        viewModelScope.launch {
            sendViewCommand(
                HomeViewCommand.Navigate(
                    screenId = AppScreen.WelcomeScreen.route,
                    shouldRemoveCurrentScreenFromStack = true,
                )
            )
        }
    }

    override fun onViewEventHappened(event: HomeViewEvent) {
//        Nothing
    }
}
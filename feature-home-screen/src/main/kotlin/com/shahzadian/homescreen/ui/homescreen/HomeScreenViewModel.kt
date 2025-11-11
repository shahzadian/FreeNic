package com.shahzadian.homescreen.ui.homescreen

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.BaseViewModel
import kotlinx.coroutines.launch

class HomeScreenViewModel : BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenCommand>() {

    override val initState: HomeScreenState by lazy {
        HomeScreenState(
            isLoading = false,
        )
    }

    init {
        viewModelScope.launch {
            sendViewCommand(
                HomeScreenCommand.Navigate(
                    screenId = AppScreen.WelcomeScreen.route,
                    shouldRemoveCurrentScreenFromStack = true,
                )
            )
        }
    }

    override fun onViewEventHappened(event: HomeScreenEvent) {
//        Nothing
    }
}
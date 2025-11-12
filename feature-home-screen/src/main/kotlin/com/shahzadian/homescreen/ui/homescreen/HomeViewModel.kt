package com.shahzadian.homescreen.ui.homescreen

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeViewState, HomeViewEvent, HomeViewCommand>() {

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
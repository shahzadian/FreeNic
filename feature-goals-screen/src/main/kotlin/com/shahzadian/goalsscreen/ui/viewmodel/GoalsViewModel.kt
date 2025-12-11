package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GoalsViewModel @Inject constructor() :
    BaseViewModel<GoalsViewState, GoalsViewEvent, GoalsViewCommand>() {

    override val initState: GoalsViewState by lazyUnsafe { GoalsViewState.Loading }

    init {
        setDataState()
    }

    override fun onViewEventHappened(event: GoalsViewEvent) {
        when (event) {
            is GoalsViewEvent.OnSavingGoalsUpgradeClicked -> {
                viewModelScope.launch {
                    sendViewCommand(GoalsViewCommand.NavigateTo(AppScreen.PaywallScreen))
                }
            }
        }
    }

    private fun setDataState() {
        viewModelScope.launch {
            setViewState(GoalsViewState.Data)
        }
    }
}

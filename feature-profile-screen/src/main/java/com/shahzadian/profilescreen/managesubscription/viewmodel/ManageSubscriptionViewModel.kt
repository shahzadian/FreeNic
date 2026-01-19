package com.shahzadian.profilescreen.managesubscription.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ManageSubscriptionViewModel @Inject constructor() :
    BaseViewModel<ManageSubscriptionViewState, ManageSubscriptionViewEvent, ManageSubscriptionViewCommand>() {

    override val initState: ManageSubscriptionViewState by lazyUnsafe {
        ManageSubscriptionViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: ManageSubscriptionViewEvent) {
        when (event) {
            is ManageSubscriptionViewEvent.OnBackIconClicked -> {
                onBackIconClicked()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            setViewState(ManageSubscriptionViewState.Data)
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(ManageSubscriptionViewCommand.NavigateBack)
        }
    }
}
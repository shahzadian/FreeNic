package com.shahzadian.slipscreen.ui.slipconfirmation.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SLipConfirmationViewModel @Inject constructor() :
    BaseViewModel<SlipConfirmationViewState, SlipConfirmationViewEvent, SlipConfirmationViewCommand>() {

    override val initState: SlipConfirmationViewState by lazyUnsafe {
        SlipConfirmationViewState.Data
    }

    override fun onViewEventHappened(event: SlipConfirmationViewEvent) {
        when (event) {
            is SlipConfirmationViewEvent.OnCancelClicked -> {
                onCancelClicked()
            }

            is SlipConfirmationViewEvent.OnConfirmClicked -> {
                onConfirmClicked()
            }
        }
    }

    private fun onConfirmClicked() {
        viewModelScope.launch {
            sendViewCommand(SlipConfirmationViewCommand.NavigateTo(AppScreen.SlipReasonScreen))
        }
    }

    private fun onCancelClicked() {
        viewModelScope.launch {
            sendViewCommand(SlipConfirmationViewCommand.NavigateBack)
        }
    }
}
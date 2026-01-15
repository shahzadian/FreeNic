package com.shahzadian.slipscreen.ui.slipconfirmation.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SLipConfirmationViewModel @Inject constructor(
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<SlipConfirmationViewState, SlipConfirmationViewEvent, SlipConfirmationViewCommand>() {

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
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.SLIP_CONFIRMATION_TAP,
                )
            )
            sendViewCommand(SlipConfirmationViewCommand.NavigateTo(AppScreen.SlipReasonScreen))
        }
    }

    private fun onCancelClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.SLIP_CONFIRMATION_CANCEL_TAP,
                )
            )
            sendViewCommand(SlipConfirmationViewCommand.NavigateBack)
        }
    }
}
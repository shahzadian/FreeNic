package com.shahzadian.slipscreen.ui.slipconfirmation.viewmodel

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SLipConfirmationViewModel @Inject constructor() :
    BaseViewModel<SlipConfirmationViewState, SlipConfirmationViewEvent, SlipConfirmationViewCommand>() {

    override val initState: SlipConfirmationViewState by lazyUnsafe {
        SlipConfirmationViewState.Data
    }

    override fun onViewEventHappened(event: SlipConfirmationViewEvent) {

    }
}
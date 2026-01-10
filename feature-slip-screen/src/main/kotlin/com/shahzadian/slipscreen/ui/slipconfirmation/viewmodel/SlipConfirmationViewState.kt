package com.shahzadian.slipscreen.ui.slipconfirmation.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface SlipConfirmationViewState : ViewState {

    @Immutable
    data object Data : SlipConfirmationViewState
}

package com.shahzadian.slipscreen.ui.slipconfirmation.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface SlipConfirmationViewCommand : ViewCommand {

    @Immutable
    data object NavigateBack : SlipConfirmationViewCommand
}

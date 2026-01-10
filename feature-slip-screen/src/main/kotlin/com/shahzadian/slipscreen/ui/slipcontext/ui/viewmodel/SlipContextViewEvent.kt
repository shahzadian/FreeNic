package com.shahzadian.slipscreen.ui.slipcontext.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface SlipContextViewEvent : ViewEvent {

    @Immutable
    data object OnConfirmClicked : SlipContextViewEvent

    @Immutable
    data object OnSkipClicked : SlipContextViewEvent
}

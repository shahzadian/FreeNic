package com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface SlipReasonViewEvent : ViewEvent {

    @Immutable
    data class OnReasonClicked(val reasonId: String) : SlipReasonViewEvent

    @Immutable
    data object OnConfirmClicked : SlipReasonViewEvent

    @Immutable
    data class OnSlipDatePicked(
        val millis: Long
    ) : SlipReasonViewEvent

    @Immutable
    data object OnSkipClicked : SlipReasonViewEvent
}

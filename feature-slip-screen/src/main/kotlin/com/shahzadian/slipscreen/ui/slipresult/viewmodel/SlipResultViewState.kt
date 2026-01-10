package com.shahzadian.slipscreen.ui.slipresult.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface SlipResultViewState : ViewState {

    @Immutable
    data object Loading : SlipResultViewState

    @Immutable
    data class Data(
        val noCigDays: Int,
    ) : SlipResultViewState

}

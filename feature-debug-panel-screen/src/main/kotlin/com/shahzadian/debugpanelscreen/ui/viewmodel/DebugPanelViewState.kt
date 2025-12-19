package com.shahzadian.debugpanelscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface DebugPanelViewState : ViewState {

    @Immutable
    data object Loading : DebugPanelViewState

    data class Data(
        val hasPaidSubscription: Boolean,
    ) : DebugPanelViewState
}

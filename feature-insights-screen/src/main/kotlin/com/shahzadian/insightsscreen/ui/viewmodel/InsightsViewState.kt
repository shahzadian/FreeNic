package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface InsightsViewState : ViewState {

    @Immutable
    data object Loading : InsightsViewState

    data object Data : InsightsViewState
}

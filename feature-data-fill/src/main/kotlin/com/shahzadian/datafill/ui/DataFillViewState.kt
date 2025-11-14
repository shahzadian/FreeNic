package com.shahzadian.datafill.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface DataFillViewState : ViewState {

    data object Loading : DataFillViewState
}

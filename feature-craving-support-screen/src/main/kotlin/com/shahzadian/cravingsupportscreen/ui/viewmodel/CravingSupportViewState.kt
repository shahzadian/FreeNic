package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface CravingSupportViewState : ViewState {

    data object Loading : CravingSupportViewState

    data object Data : CravingSupportViewState
}
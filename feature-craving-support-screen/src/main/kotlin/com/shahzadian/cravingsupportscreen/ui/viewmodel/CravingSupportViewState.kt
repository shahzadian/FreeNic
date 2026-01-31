package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface CravingSupportViewState : ViewState {

    data object Loading : CravingSupportViewState

    data class Data(
        val motivationalNote: TextWrapper,
    ) : CravingSupportViewState
}

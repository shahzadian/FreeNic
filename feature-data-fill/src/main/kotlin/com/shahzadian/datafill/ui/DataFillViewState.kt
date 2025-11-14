package com.shahzadian.datafill.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface DataFillViewState : ViewState {

    data object Loading : DataFillViewState

    @Immutable
    data class About(
        val name: String? = null,
        val age: String? = null,
    ) : DataFillViewState
}

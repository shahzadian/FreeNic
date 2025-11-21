package com.shahzadian.mainscreen.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.mainscreen.ui.model.MainScreenItemUiModel

@Immutable
internal sealed interface MainViewState : ViewState {

    @Immutable
    data object Loading : MainViewState

    @Immutable
    data class Default(val pages: List<MainScreenItemUiModel>) : MainViewState
}

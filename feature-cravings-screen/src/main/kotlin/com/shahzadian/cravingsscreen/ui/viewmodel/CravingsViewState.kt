package com.shahzadian.cravingsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface CravingsViewState : ViewState {

    data object Loading : CravingsViewState

    @Immutable
    data class Data(
        val tools: List<CravingsToolUiModel>,
        val showAllButton: Boolean,
        val onAllButtonClick: () -> Unit,
    ) : CravingsViewState {

        @Immutable
        internal data class CravingsToolUiModel(
            val icon: ImageWrapper,
            val title: TextWrapper,
            val subtitle: TextWrapper,
            val onClick: () -> Unit,
        )
    }
}
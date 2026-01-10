package com.shahzadian.slipscreen.ui.slipcontext.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal interface SlipContextViewState : ViewState {

    @Immutable
    data object Loading : SlipContextViewState

    @Immutable
    data class Data(
        val slipContexts: List<SlipContextUiModel>,
        val selectedContextId: String?,
        val isSubmitButtonEnabled: Boolean,
    ) : SlipContextViewState

    @Immutable
    data class SlipContextUiModel(
        val id: String,
        val name: TextWrapper,
        val icon: ImageWrapper,
    )
}
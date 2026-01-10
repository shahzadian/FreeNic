package com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal interface SlipReasonViewState : ViewState {

    @Immutable
    data object Loading : SlipReasonViewState

    @Immutable
    data class Data(
        val slipReasons: List<SlipReasonUiModel>,
        val selectedReasonId: String?,
        val isSubmitButtonEnabled: Boolean,
        val slipDateUiModel: SlipDateUiModel,
    ) : SlipReasonViewState

    @Immutable
    data class SlipReasonUiModel(
        val id: String,
        val name: TextWrapper,
        val icon: ImageWrapper,
    )

    @Immutable
    data class SlipDateUiModel(
        val millis: Long,
        val displayableDate: TextWrapper,
    )
}
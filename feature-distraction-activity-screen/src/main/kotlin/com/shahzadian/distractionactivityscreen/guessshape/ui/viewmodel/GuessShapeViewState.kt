package com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper

@Immutable
internal sealed interface GuessShapeViewState : ViewState {

    @Immutable
    data object Loading : GuessShapeViewState

    @Immutable
    data class ShowShapes(
        val shapes: List<ShapeUiModel>,
        val showTimeSeconds: Int,
    ) : GuessShapeViewState

    @Immutable
    data class GuessShape(
        val shapes: List<ShapeUiModel>,
    ) : GuessShapeViewState

    @Immutable
    data class Result(
        val didUserWin: Boolean,
    ) : GuessShapeViewState

    @Immutable
    data class ShapeUiModel(
        val id: String,
        val icon: ImageWrapper,
    )
}

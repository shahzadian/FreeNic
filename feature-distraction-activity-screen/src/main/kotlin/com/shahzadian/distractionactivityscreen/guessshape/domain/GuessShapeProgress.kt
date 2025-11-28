package com.shahzadian.distractionactivityscreen.guessshape.domain

internal sealed interface GuessShapeProgress {

    data object Loading : GuessShapeProgress

    data class ShowShapes(
        val shapes: List<GuessShapeModel>,
        val showTimeSeconds: Int,
    ) : GuessShapeProgress

    data class GuessShape(
        val shapes: List<GuessShapeModel>,
    ) : GuessShapeProgress

    data class ShowResult(
        val didUserWin: Boolean,
    ) : GuessShapeProgress
}
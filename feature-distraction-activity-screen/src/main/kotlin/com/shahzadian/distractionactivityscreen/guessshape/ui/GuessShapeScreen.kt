package com.shahzadian.distractionactivityscreen.guessshape.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.ImageWrapper
import com.shahzadian.distractionactivityscreen.R
import com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel.GuessShapeViewState

@Composable
fun GuessShapeScreen(
    modifier: Modifier = Modifier,
) {
    ShowShapesView(
        showTimeSeconds = 5,
        shapes = listOf(
            GuessShapeViewState.ShapeUiModel(
                icon = ImageWrapper.FromRes(
                    R.drawable.ic_guess_shape_square,
                ),
                name = "Square",
            ),
            GuessShapeViewState.ShapeUiModel(
                icon = ImageWrapper.FromRes(
                    R.drawable.ic_guess_shape_circle,
                ),
                name = "Circle",
            ),
            GuessShapeViewState.ShapeUiModel(
                icon = ImageWrapper.FromRes(
                    R.drawable.ic_guess_shape_triangle,
                ),
                name = "Triangle",
            )
        ),
        onTimeExpired = {},
        modifier = modifier.padding(16.dp).fillMaxWidth()
    )
}


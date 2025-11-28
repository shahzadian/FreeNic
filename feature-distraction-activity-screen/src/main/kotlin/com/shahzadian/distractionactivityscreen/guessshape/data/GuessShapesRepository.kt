package com.shahzadian.distractionactivityscreen.guessshape.data

import com.freenic.ui.wrapper.ImageWrapper
import com.shahzadian.distractionactivityscreen.R
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeModel
import javax.inject.Inject

internal interface GuessShapesRepository {

    suspend fun getShapes(): List<GuessShapeModel>
}

internal class GuessShapesRepositoryImpl @Inject constructor() : GuessShapesRepository {

    override suspend fun getShapes(): List<GuessShapeModel> {
        return listOf(
            GuessShapeModel(
                id = "square",
                icon = ImageWrapper.FromRes(
                    R.drawable.ic_guess_shape_square,
                )
            ),
            GuessShapeModel(
                id = "circle",
                icon = ImageWrapper.FromRes(
                    R.drawable.ic_guess_shape_circle,
                )
            ),
            GuessShapeModel(
                id = "triangle",
                icon = ImageWrapper.FromRes(
                    R.drawable.ic_guess_shape_triangle,
                )
            ),
            GuessShapeModel(
                id = "star",
                icon = ImageWrapper.FromRes(
                    R.drawable.ic_guess_shape_star,
                )
            ),
            GuessShapeModel(
                id = "trapezoid",
                icon = ImageWrapper.FromRes(
                    R.drawable.ic_guess_shape_trapezoid,
                )
            ),
        )
    }
}

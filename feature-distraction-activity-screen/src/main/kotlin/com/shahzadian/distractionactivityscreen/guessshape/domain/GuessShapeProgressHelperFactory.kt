package com.shahzadian.distractionactivityscreen.guessshape.domain

import com.shahzadian.distractionactivityscreen.guessshape.data.GuessShapesRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal interface GuessShapeProgressHelperFactory {

    fun create(
        coroutineScope: CoroutineScope,
    ): GuessShapeProgressHelper
}

internal class GuessShapeProgressHelperFactoryImpl @Inject constructor(
    private val guessShapesRepository: GuessShapesRepository,
) : GuessShapeProgressHelperFactory {

    override fun create(coroutineScope: CoroutineScope): GuessShapeProgressHelper {
        return GuessShapeProgressHelperImpl(
            coroutineScope = coroutineScope,
            guessShapesRepository = guessShapesRepository,
        )
    }
}

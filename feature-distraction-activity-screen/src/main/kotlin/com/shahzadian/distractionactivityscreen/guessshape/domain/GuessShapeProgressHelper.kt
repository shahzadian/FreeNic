package com.shahzadian.distractionactivityscreen.guessshape.domain

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.distractionactivityscreen.guessshape.data.GuessShapesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal interface GuessShapeProgressHelper {

    val progressStateFlow: StateFlow<GuessShapeProgress>

    fun onShowTimeExpired()

    fun onShapeGuessed(id: String)
}

internal class GuessShapeProgressHelperImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val guessShapesRepository: GuessShapesRepository,
) : GuessShapeProgressHelper {

    override val progressStateFlow: MutableStateFlow<GuessShapeProgress> by lazyUnsafe {
        MutableStateFlow(GuessShapeProgress.Loading)
    }

    private var shapes: List<GuessShapeModel> = emptyList()
    private var shapeIdToGuess: String? = null

    init {
        coroutineScope.launch {
            shapes = guessShapesRepository.getShapes()
            configureNewActivity()
        }
    }

    override fun onShowTimeExpired() {
        coroutineScope.launch {
            progressStateFlow.emit(
                GuessShapeProgress.GuessShape(
                    shapes = shapes,
                )
            )
        }
    }

    override fun onShapeGuessed(id: String) {
        val didUserWin = shapeIdToGuess == id
        coroutineScope.launch {
            progressStateFlow.emit(GuessShapeProgress.ShowResult(didUserWin))
        }
    }

    private fun configureNewActivity() {
        if (shapes.isEmpty()) return
        shapeIdToGuess = shapes.random().id
        coroutineScope.launch {
            progressStateFlow.emit(
                GuessShapeProgress.ShowShapes(
                    shapes = shapes.filter { it.id != shapeIdToGuess },
                    showTimeSeconds = SHAPE_SHOW_TIME_SECONDS,
                )
            )
        }
    }

    companion object {
        private const val SHAPE_SHOW_TIME_SECONDS: Int = 5
    }
}
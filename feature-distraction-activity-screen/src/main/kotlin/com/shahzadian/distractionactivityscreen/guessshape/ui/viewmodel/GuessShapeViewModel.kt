package com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeModel
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeProgress
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeProgressHelper
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeProgressHelperFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GuessShapeViewModel @Inject constructor(
    private val guessShapeProgressHelperFactory: GuessShapeProgressHelperFactory,
) : BaseViewModel<GuessShapeViewState, GuessShapeViewEvent, GuessShapeViewCommand>() {

    override val initState: GuessShapeViewState by lazyUnsafe {
        GuessShapeViewState.Loading
    }

    private var progressHelper: GuessShapeProgressHelper? = null

    private var currentProgressJob: Job? = null

    init {
        startActivity()
    }

    override fun onViewEventHappened(event: GuessShapeViewEvent) {
        when (event) {
            is GuessShapeViewEvent.OnShapeShowTimeExpired -> {
                progressHelper?.onShowTimeExpired()
            }

            is GuessShapeViewEvent.OnShapeGuessed -> {
                progressHelper?.onShapeGuessed(event.shapeId)
            }

            is GuessShapeViewEvent.OnRestartClicked -> {
                startActivity()
            }
        }
    }

    private fun startActivity() {
        currentProgressJob?.cancel()
        val progressHelper = guessShapeProgressHelperFactory.create(viewModelScope)
        this.progressHelper = progressHelper
        currentProgressJob = progressHelper
            .progressStateFlow
            .onEach { state ->
                val viewState = state.toUiState()
                setViewState(viewState)
            }
            .launchIn(viewModelScope)
    }

    private fun GuessShapeProgress.toUiState(): GuessShapeViewState {
        return when (this) {
            is GuessShapeProgress.Loading -> GuessShapeViewState.Loading
            is GuessShapeProgress.ShowShapes -> GuessShapeViewState.ShowShapes(
                shapes = this.shapes.map { it.toUiModel() },
                showTimeSeconds = this.showTimeSeconds,
            )

            is GuessShapeProgress.GuessShape -> GuessShapeViewState.GuessShape(
                shapes = this.shapes.map { it.toUiModel() },
            )

            is GuessShapeProgress.ShowResult -> GuessShapeViewState.Result(
                didUserWin = this.didUserWin,
            )
        }
    }

    private fun GuessShapeModel.toUiModel(): GuessShapeViewState.ShapeUiModel {
        return GuessShapeViewState.ShapeUiModel(
            id = this.id,
            icon = this.icon,
        )
    }
}

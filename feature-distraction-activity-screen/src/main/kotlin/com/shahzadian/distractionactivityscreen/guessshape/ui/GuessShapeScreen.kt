package com.shahzadian.distractionactivityscreen.guessshape.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel.GuessShapeViewEvent
import com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel.GuessShapeViewModel
import com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel.GuessShapeViewState

@Composable
fun GuessShapeScreen(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    val viewModel: GuessShapeViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    val onEvent: (GuessShapeViewEvent) -> Unit = remember {
        { event -> viewModel.onViewEvent(event) }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (state) {
            is GuessShapeViewState.Loading -> {}
            is GuessShapeViewState.ShowShapes -> {
                ShapeShowStateView(
                    showTimeSeconds = (state as GuessShapeViewState.ShowShapes).showTimeSeconds,
                    shapes = (state as GuessShapeViewState.ShowShapes).shapes,
                    onTimeExpired = {
                        onEvent(GuessShapeViewEvent.OnShapeShowTimeExpired)
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                )
            }

            is GuessShapeViewState.GuessShape -> {
                GuessShapeView(
                    shapes = (state as GuessShapeViewState.GuessShape).shapes,
                    onShapeSelected = {
                        onEvent(GuessShapeViewEvent.OnShapeGuessed(it))
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            }

            is GuessShapeViewState.Result -> {
                GuessShapeResultView(
                    didUserWin = (state as GuessShapeViewState.Result).didUserWin,
                    onRestartClicked = {
                        onEvent(GuessShapeViewEvent.OnRestartClicked)
                    },
                    onCloseClicked = {
                        navigator.navigateBack()
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun ShapeShowStateView(
    showTimeSeconds: Int,
    shapes: List<GuessShapeViewState.ShapeUiModel>,
    onTimeExpired: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ShowShapesView(
        showTimeSeconds = showTimeSeconds,
        shapes = shapes,
        onTimeExpired = onTimeExpired,
        modifier = modifier,
    )
}

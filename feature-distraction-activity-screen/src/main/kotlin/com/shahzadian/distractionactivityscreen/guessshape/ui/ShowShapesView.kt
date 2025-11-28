package com.shahzadian.distractionactivityscreen.guessshape.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.shahzadian.baseui.R
import com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel.GuessShapeViewState

private const val ANIMATION_DURATION_SHAPE_APPEARANCE: Int = 1000

@Composable
internal fun ShowShapesView(
    shapes: List<GuessShapeViewState.ShapeUiModel>,
    showTimeSeconds: Int,
    onTimeExpired: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val timeExpired = remember { { onTimeExpired() } }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        HeaderView(
            modifier = Modifier.padding(bottom = 16.dp),
        )
        ShapesView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shapes = shapes,
        )
        ProgressView(
            showTimeSeconds = showTimeSeconds,
            appearanceDelayMillis = ANIMATION_DURATION_SHAPE_APPEARANCE,
            onTimeExpired = timeExpired,
            modifier = modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.guess_shape_showing_title),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
    )
}

@Composable
private fun ShapesView(
    shapes: List<GuessShapeViewState.ShapeUiModel>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        shapes.forEachIndexed { index, shape ->
            var imageVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { imageVisible = true }
            AnimatedVisibility(
                visible = imageVisible,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = ANIMATION_DURATION_SHAPE_APPEARANCE,
                        delayMillis = index * 200
                    )
                ),
                modifier = Modifier.weight(1f),
            ) {
                Image(
                    painter = imageFromWrapper(shape.icon),
                    contentDescription = shape.id,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ProgressView(
    showTimeSeconds: Int,
    appearanceDelayMillis: Int,
    onTimeExpired: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var progress by remember { mutableStateOf(0F) }
    var visible by remember { mutableStateOf(false) }
    val progressAnimDuration = showTimeSeconds * 1000
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = progressAnimDuration,
            delayMillis = appearanceDelayMillis,
            easing = LinearEasing,
        ),
    )
    if (progressAnimation >= 100f) {
        onTimeExpired()
    }
    LaunchedEffect(Unit) {
        progress = 100f
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = appearanceDelayMillis,
            )
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        LinearProgressIndicator(
            progress = {
                progressAnimation / 100
            },
            modifier = modifier.height(2.dp).padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

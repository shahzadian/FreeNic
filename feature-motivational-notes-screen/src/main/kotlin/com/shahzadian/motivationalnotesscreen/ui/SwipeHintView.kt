package com.shahzadian.motivationalnotesscreen.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.motivationalnotesscreen.R
import kotlinx.coroutines.delay

@Composable
internal fun SwipeHintView(
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(3000)
        visible = false
    }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
    )
    Column(
        modifier = modifier
            .alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FingerView(
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.motivational_notes_swipe_hint),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun FingerView(
    modifier: Modifier = Modifier,
) {
    // 1. Define a state to hold the target position (e.g., a boolean to switch between two states)
    var shifted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        shifted = true
    }

    // 2. Calculate the target offset in Dp (Density-independent Pixels)
    // Target is 100.dp to the right when shifted is true, 0.dp otherwise
    val targetOffsetX = if (shifted) (-20).dp else 0.dp

    // 3. Use animateDpAsState to smoothly transition the value
    val offsetX by animateDpAsState(
        targetValue = targetOffsetX,
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(
                durationMillis = 500,
                delayMillis = 500
            ),
            repeatMode = RepeatMode.Reverse,
        ), // Customize animation spec (e.g., tween, spring)
        label = "translationX_animation",
        finishedListener = {
            shifted = false
        }
    )
    Image(
        painter = painterResource(R.drawable.ic_motivational_notes_swipe_hint),
        contentDescription = "swipe hint",
        modifier = modifier.offset(x = offsetX)
    )
}

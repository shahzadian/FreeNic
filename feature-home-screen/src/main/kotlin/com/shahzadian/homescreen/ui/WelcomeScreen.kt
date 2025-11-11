package com.shahzadian.homescreen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R
import com.shahzadian.homescreen.ui.UiUtils.DELAY_ANIMATION_VISIBILITY_TITLE
import com.shahzadian.homescreen.ui.UiUtils.DURATION_ANIMATION_VISIBILITY

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
) {
    var contentVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { contentVisible = true }

    val gradient = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondaryContainer
        ),
        tileMode = TileMode.Mirror
    )
    AnimatedVisibility(
        visible = contentVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY
            )
        ),
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Content(
            onClick = {

            }
        )
    }
}

@Composable
private fun Content(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeImage(
            modifier = Modifier
                .padding(bottom = 32.dp)
        )
        Title(
            modifier = Modifier
                .padding(bottom = 12.dp),
        )
        Description(
            modifier = Modifier
                .padding(bottom = 32.dp)
        )
        StartButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun WelcomeImage(
    modifier: Modifier = Modifier,
) {
    var imageVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { imageVisible = true }

    val infiniteTransition = rememberInfiniteTransition(label = "heartbeat")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(1000)
        ),
        label = "scale"
    )
    AnimatedVisibility(
        visible = imageVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY,
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY,
            )
        ),
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(com.shahzadian.homescreen.R.drawable.ic_welcome_screen),
            contentDescription = "Welcome screen",
            modifier = Modifier.scale(scale)
        )
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
) {
    var titleVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { titleVisible = true }
    AnimatedVisibility(
        visible = titleVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY,
                delayMillis = DELAY_ANIMATION_VISIBILITY_TITLE,
            )
        ),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.welcome_screen_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
private fun Description(
    modifier: Modifier = Modifier,
) {
    var descriptionVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { descriptionVisible = true }
    AnimatedVisibility(
        visible = descriptionVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY,
                delayMillis = DELAY_ANIMATION_VISIBILITY_TITLE,
            )
        ),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.welcome_screen_desc),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun StartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val animationDelay = DELAY_ANIMATION_VISIBILITY_TITLE * 2
    var buttonVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { buttonVisible = true }

    val elevation by animateDpAsState(
        targetValue = if (buttonVisible) 4.dp else 0.dp,
        animationSpec = tween(
            durationMillis = DURATION_ANIMATION_VISIBILITY,
            delayMillis = animationDelay + 500 // Compose bug. If there is no delay, elevation does not animate
        )
    )
    AnimatedVisibility(
        visible = buttonVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY / 2,
                delayMillis = animationDelay,
            )
        ),
        modifier = modifier,
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = elevation,
                pressedElevation = elevation * 2,
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = modifier
                .height(56.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_screen_button),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

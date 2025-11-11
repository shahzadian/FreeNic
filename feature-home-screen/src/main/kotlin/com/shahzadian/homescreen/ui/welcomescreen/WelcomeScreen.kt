package com.shahzadian.homescreen.ui.welcomescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R

private const val DURATION_ANIMATION: Int = 1000
private const val DELAY_ANIMATION: Int = 500

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
                durationMillis = DURATION_ANIMATION
            )
        ),
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Content()
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        WelcomeImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp)
        )
        Title(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp),
        )
        Description(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun WelcomeImage(
    modifier: Modifier = Modifier,
) {
    var imageVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { imageVisible = true }
    AnimatedVisibility(
        visible = imageVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION,
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION,
            )
        ),
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(com.shahzadian.homescreen.R.drawable.ic_welcome_screen),
            contentDescription = "Welcome screen",
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
                durationMillis = DURATION_ANIMATION,
                delayMillis = DELAY_ANIMATION,
            )
        ),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.home_screen_title),
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
                durationMillis = DURATION_ANIMATION,
                delayMillis = DELAY_ANIMATION,
            )
        ),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.home_screen_desc),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }
}

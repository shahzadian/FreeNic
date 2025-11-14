package com.shahzadian.homescreen.ui.welcomescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.mainGradientBackground
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.homescreen.ui.UiUtils.DELAY_ANIMATION_VISIBILITY_TITLE
import com.shahzadian.homescreen.ui.UiUtils.DURATION_ANIMATION_VISIBILITY

@Composable
fun WelcomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: WelcomeViewModel = hiltViewModel()
    val onClick: (WelcomeViewEvent) -> Unit = remember {
        { event -> viewModel.onViewEvent(event) }
    }
    var contentVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { contentVisible = true }

    val gradient = mainGradientBackground()
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
                onClick(WelcomeViewEvent.OnGetStartedClicked)
            }
        )
    }
    LaunchedEffect(key1 = viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is WelcomeViewCommand.Navigate -> {
                        navController.navigate(command.screen.route)
                    }
                }
            }
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var contentVisible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) { contentVisible = true }
        AnimatedVisibility(
            modifier = modifier.weight(1f),
            visible = contentVisible,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = DURATION_ANIMATION_VISIBILITY * 2,
                )
            ) + fadeIn(
                animationSpec = tween(
                    durationMillis = DURATION_ANIMATION_VISIBILITY,
                )
            ),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                )
                Description(
                )
            }
        }

        StartButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp)
        )
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.welcome_screen_title),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Description(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.welcome_screen_desc),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun StartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val animationDelay = DELAY_ANIMATION_VISIBILITY_TITLE * 2
    var buttonVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { buttonVisible = true }

    AnimatedVisibility(
        visible = buttonVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY,
                delayMillis = animationDelay,
            )
        ),
        modifier = modifier,
    ) {
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.welcome_screen_button),
            onClick = onClick,
            modifier = Modifier
        )
    }
}

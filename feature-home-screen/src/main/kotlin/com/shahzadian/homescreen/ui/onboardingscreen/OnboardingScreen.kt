package com.shahzadian.homescreen.ui.onboardingscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.homescreen.ui.UiUtils.DURATION_ANIMATION_VISIBILITY

private const val DURATION_ANIMATION_IMAGE_SCALE: Int = 20_000
private const val DURATION_ANIMATION_TITLE_SLIDE: Int = 1500
private const val DELAY_ANIMATION_TEXT_SLIDE: Int = 1000


@Composable
fun OnboardingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is OnboardingViewState.Loading -> {}
        is OnboardingViewState.Data -> {
            Content(
                modifier = modifier,
                data = state as OnboardingViewState.Data
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    data: OnboardingViewState.Data
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        OnboardingImage(
            image = data.image
        )
        TextContent(
            title = data.title,
            subtitle = data.subtitle,
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .padding(bottom = screenHeight / 4)
        )
    }
}

@Composable
private fun OnboardingImage(
    image: ImageWrapper,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1.8f else 1f,
        animationSpec = tween(
            durationMillis = DURATION_ANIMATION_IMAGE_SCALE,
            easing = LinearOutSlowInEasing
        )
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY * 4
            )
        ),
        modifier = modifier
            .fillMaxSize()
            .scale(scale = scale)
    ) {
        Image(
            painter = imageFromWrapper(image),
            contentDescription = "Onboarding"
        )
    }
}

@Composable
private fun TextContent(
    title: TextWrapper,
    subtitle: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        TitleText(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
        )
        SubtitleText(
            text = subtitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

@Composable
private fun TitleText(
    text: TextWrapper,
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_TITLE_SLIDE,
                delayMillis = DELAY_ANIMATION_TEXT_SLIDE,
                easing = LinearOutSlowInEasing
            ),
            initialOffsetX = { -it },
        )
    ) {
        Surface(
            modifier = modifier.padding(horizontal = 16.dp),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.background,
        ) {
            Text(
                text = textFromWrapper(text),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun SubtitleText(
    text: TextWrapper,
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_TITLE_SLIDE,
                delayMillis = DELAY_ANIMATION_TEXT_SLIDE * 3,
                easing = LinearOutSlowInEasing
            ),
            initialOffsetX = { it * 2 },
        )
    ) {
        Surface(
            modifier = modifier.padding(horizontal = 16.dp),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            Text(
                text = textFromWrapper(text),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

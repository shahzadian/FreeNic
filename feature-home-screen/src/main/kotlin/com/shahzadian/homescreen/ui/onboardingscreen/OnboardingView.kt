package com.shahzadian.homescreen.ui.onboardingscreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R
import com.shahzadian.homescreen.ui.UiUtils.DELAY_ANIMATION_VISIBILITY_TITLE
import com.shahzadian.homescreen.ui.UiUtils.DURATION_ANIMATION_VISIBILITY

@Composable
@Preview
fun Test() {
    OnboardingView(
        titleRes = R.string.welcome_screen_title,
        descriptionRes = R.string.welcome_screen_desc,
        imageRes = com.shahzadian.homescreen.R.drawable.ic_welcome_screen,
        buttonTextRes = R.string.welcome_screen_button,
        onButtonClick = {},
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
internal fun OnboardingView(
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    @DrawableRes imageRes: Int,
    @StringRes buttonTextRes: Int,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContentImage(
            imageRes = imageRes,
            modifier = Modifier
                .padding(bottom = 32.dp)
        )
        OnboardingTitle(
            textRes = titleRes,
            modifier = Modifier
                .padding(bottom = 12.dp),
        )
        OnboardingDescription(
            textRes = descriptionRes,
            modifier = Modifier
                .padding(bottom = 32.dp)
        )
        SubmitButton(
            textRes = buttonTextRes,
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ContentImage(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
) {
    var imageVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { imageVisible = true }
    val scale by animateFloatAsState(
        targetValue = if (imageVisible) 1.5f else 1f,
        animationSpec = tween(
            durationMillis = DURATION_ANIMATION_VISIBILITY,
            delayMillis = DURATION_ANIMATION_VISIBILITY,
        )
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
        modifier = modifier
            .scale(scale),
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = "Onboarding view",
          //  modifier = Modifier.scale(scale),
        )
    }
}

@Composable
private fun OnboardingTitle(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
) {
    var titleVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { titleVisible = true }
    AnimatedVisibility(
        visible = titleVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY,
                delayMillis = DELAY_ANIMATION_VISIBILITY_TITLE + DELAY_ANIMATION_VISIBILITY_TITLE / 2,
            )
        ),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(textRes),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
private fun OnboardingDescription(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
) {
    var descriptionVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { descriptionVisible = true }
    AnimatedVisibility(
        visible = descriptionVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_VISIBILITY,
                delayMillis = DELAY_ANIMATION_VISIBILITY_TITLE * 2,
            )
        ),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(textRes),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SubmitButton(
    @StringRes textRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

}

package com.shahzadian.datafill.ui.pages.result

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shahzadian.datafill.R
import com.shahzadian.datafill.ui.DataFillViewState

private const val ANIMATION_DURATION_IMAGE_APPEARANCE: Int = 1000

@Composable
internal fun DataFillResultPage(
    userData: DataFillViewState.Result.UserData,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        IconView(
            modifier = Modifier.padding(bottom = 24.dp),
        )
        HeaderTexts(
            modifier = Modifier.padding(bottom = 48.dp),
        )
        CardView(
            userData = DataFillViewState.Result.UserData(
                name = userData.name,
                age = userData.age,
                cigarettesPerDay = userData.cigarettesPerDay
            ),
        )
    }
}

@Composable
private fun IconView(
    modifier: Modifier = Modifier,
) {
    var imageVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { imageVisible = true }
    AnimatedVisibility(
        visible = imageVisible,
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_IMAGE_APPEARANCE
            ),
            initialOffsetY = { fullHeight -> fullHeight * 2 },
        ) + scaleIn(
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_IMAGE_APPEARANCE * 2
            ),
            initialScale = 0.7f,
        ),
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_data_fill_result_header),
            contentDescription = "data fill result",
        )
    }
}

@Composable
private fun HeaderTexts(
    modifier: Modifier = Modifier,
) {
    var textVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { textVisible = true }

    AnimatedVisibility(
        visible = textVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_IMAGE_APPEARANCE,
                delayMillis = ANIMATION_DURATION_IMAGE_APPEARANCE / 2,
            ),
        )
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.data_fill_result_title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.data_fill_result_subtitle),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun CardView(
    userData: DataFillViewState.Result.UserData,
    modifier: Modifier = Modifier,
) {
    var cardVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { cardVisible = true }
    AnimatedVisibility(
        visible = cardVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_IMAGE_APPEARANCE,
                delayMillis = ANIMATION_DURATION_IMAGE_APPEARANCE,
            ),
        ),
        modifier = modifier,
    ) {
        DataFillResultCardPage(
            userData = userData,
        )
    }
}
package com.shahzadian.datafill.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shahzadian.datafill.R

private const val ANIMATION_DURATION_IMAGE_APPEARANCE: Int = 700

@Composable
internal fun DataFillInitPage(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        ImageView(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        TitleView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        SubtitleView(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ImageView(
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_IMAGE_APPEARANCE
            ),
            initialOffsetY = { fullHeight -> fullHeight * 2 },
        )
    ) {
        Image(
            painter = painterResource(R.drawable.ic_data_fill_start_view),
            contentDescription = "Data fill"
        )
    }
}

@Composable
private fun TitleView(
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_IMAGE_APPEARANCE,
                delayMillis = ANIMATION_DURATION_IMAGE_APPEARANCE,
            )
        )
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.data_fill_start_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun SubtitleView(
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_IMAGE_APPEARANCE,
                delayMillis = ANIMATION_DURATION_IMAGE_APPEARANCE + ANIMATION_DURATION_IMAGE_APPEARANCE / 4,
            )
        )
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.data_fill_start_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

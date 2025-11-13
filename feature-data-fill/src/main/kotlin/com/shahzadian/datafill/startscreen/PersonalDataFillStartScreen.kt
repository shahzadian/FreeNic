package com.shahzadian.datafill.startscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainGradientBackground
import com.freenic.ui.extensions.screenHeight
import com.shahzadian.datafill.R

private const val DURATION_ANIMATION_IMAGE: Int = 1000

@Composable
fun PersonalDataFillStartScreen(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradientBackground())
    ) {
        ImageContent(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = screenHeight.dp / 6)
        )
    }
}

@Composable
private fun ImageContent(
    modifier: Modifier = Modifier,
) {
    var imageVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { imageVisible = true }

    AnimatedVisibility(
        visible = imageVisible,
        modifier = modifier,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = DURATION_ANIMATION_IMAGE
            )
        )
    ) {
        Image(
            painter = painterResource(R.drawable.ic_personal_data_fill_start),
            contentDescription = "Personal data fill start image"
        )
    }
}

private fun TextContent() {

}
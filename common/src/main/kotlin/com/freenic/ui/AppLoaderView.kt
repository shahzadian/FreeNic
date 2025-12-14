package com.freenic.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R

private const val ANIMATION_DURATION: Int = 800
private const val ANIMATION_DELAY: Int = 200

@Composable
fun AppLoaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val layerIconSize = 100.dp
        LayerView(
            layerRes = R.drawable.ic_app_icon_layer_1,
            layerIndex = 0,
            modifier = Modifier.width(layerIconSize),
        )
        LayerView(
            layerRes = R.drawable.ic_app_icon_layer_2,
            layerIndex = 1,
            modifier = Modifier.width(layerIconSize),
        )
        LayerView(
            layerRes = R.drawable.ic_app_icon_layer_3,
            layerIndex = 2,
            modifier = Modifier.width(layerIconSize),
        )
    }
}

@Composable
private fun LayerView(
    layerRes: Int,
    layerIndex: Int,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "App icon layer animation: $layerIndex")
    val alpha by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ANIMATION_DURATION,
            ),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(ANIMATION_DELAY * layerIndex)
        ),
        label = "App icon layer animation"
    )
    Image(
        painter = painterResource(layerRes),
        contentDescription = "layer",
        modifier = modifier
            .alpha(alpha = alpha)
    )
}

@Composable
private fun IconView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        val transition = rememberInfiniteTransition(label = "App icon animation")
        val scale by transition.animateFloat(
            initialValue = 0.9f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                ),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "Lungs Recovery Progress"
        )
        Image(
            painter = painterResource(R.drawable.ic_app_icon_transparent_bg),
            contentDescription = "app icon",
            modifier = modifier
                .wrapContentHeight()
                .align(Alignment.Center)
                .size(30.dp)
                .scale(scale)
        )
    }
}
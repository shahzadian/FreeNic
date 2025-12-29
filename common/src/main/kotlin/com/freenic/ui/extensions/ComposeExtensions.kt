package com.freenic.ui.extensions

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

object ComposeConst {
    const val PROGRESS_ANIMATION_DURATION: Int = 1500
}

@Composable
fun imageFromWrapper(wrapper: ImageWrapper): Painter {
    return when (wrapper) {
        is ImageWrapper.FromRes -> {
            painterResource(wrapper.resId)
        }
    }
}

@Composable
fun textFromWrapper(wrapper: TextWrapper): String {
    return when (wrapper) {
        is TextWrapper.FromRes -> {
            stringResource(
                id = wrapper.resId,
                formatArgs = wrapper.args.toTypedArray()
            )
        }

        is TextWrapper.FromString -> wrapper.text
    }
}

fun Context.textFromWrapper(wrapper: TextWrapper): String {
    return when (wrapper) {
        is TextWrapper.FromRes -> {
            getString(wrapper.resId, wrapper.args)
        }

        is TextWrapper.FromString -> wrapper.text
    }
}

@Composable
fun MainSubmitButton(
    text: TextWrapper,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    style: TextStyle = MaterialTheme.typography.titleLarge,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        shape = MaterialTheme.shapes.medium,
        interactionSource = interactionSource,
        enabled = enabled,
        modifier = modifier
            .height(56.dp)
    ) {
        Text(
            text = textFromWrapper(text),
            color = MaterialTheme.colorScheme.onPrimary,
            style = style,
        )
    }
}

@Composable
fun mainGradientBackground(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.background
        ),
        tileMode = TileMode.Mirror
    )
}

@Composable
fun MainAnimatableLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    animationDuration: Int = ComposeConst.PROGRESS_ANIMATION_DURATION,
) {
    val valueAnimatable = remember { Animatable(progress) }
    LaunchedEffect(progress) {
       valueAnimatable.snapTo(0f)
       valueAnimatable.animateTo(
           targetValue = progress,
           animationSpec = tween(durationMillis = animationDuration)
       )
    }
    LinearProgressIndicator(
        progress = { valueAnimatable.value / 100f },
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(MaterialTheme.shapes.small),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}

@Composable
fun colorFromHex(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}

val screenWidth: Int
    @Composable
    get() {
        val configuration = LocalConfiguration.current
        return configuration.screenWidthDp
    }

val screenHeight: Int
    @Composable
    get() {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp
    }

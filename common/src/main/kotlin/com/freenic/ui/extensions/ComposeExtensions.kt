package com.freenic.ui.extensions

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
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

fun TextWrapper.extract(context: Context): String {
    return when (this) {
        is TextWrapper.FromRes -> context.getString(resId, args)
        is TextWrapper.FromPlural -> context.resources.getQuantityString(resId, quantity, args)
        is TextWrapper.FromString -> text
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

        is TextWrapper.FromPlural -> {
            pluralStringResource(
                id = wrapper.resId,
                count = wrapper.quantity,
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

        is TextWrapper.FromPlural -> {
            resources.getQuantityString(wrapper.resId, wrapper.quantity, wrapper.args)
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

@Composable
fun Modifier.mainHintBackground(): Modifier {
    return this
        .background(
            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
            shape = MaterialTheme.shapes.medium,
        )
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.tertiary,
            shape = MaterialTheme.shapes.medium,
        )
}

@Composable
fun Modifier.mainCardBackground(
    shape: Shape = MaterialTheme.shapes.medium,
): Modifier {
    return this
        .background(
            color = mainCardBackground,
            shape = shape,
        )
        .border(
            width = mainCardBorderWidth,
            color = mainCardBorderColor,
            shape = shape,
        )
}

@Composable
fun Modifier.noIndicationClick(
    onClick: () -> Unit,
): Modifier {
    return clickable(
        onClick = onClick,
        indication = null,
        interactionSource = null,
    )
}

val mainCardBackground: Color
    @Composable
    get() = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

val mainCardBorderColor: Color
    @Composable
    get() = MaterialTheme.colorScheme.primary

val mainCardBorderWidth: Dp
    get() = 1.dp

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

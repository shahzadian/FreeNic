package com.freenic.ui.extensions

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

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

@Composable
fun MainSubmitButton(
    text: TextWrapper,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
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
            style = MaterialTheme.typography.titleLarge,
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

val screenHeight: Int
    @Composable
    get() {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp
    }

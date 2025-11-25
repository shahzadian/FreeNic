package com.shahzadian.cravingsscreen.ui

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.cravingsscreen.R
import com.shahzadian.cravingsscreen.ui.model.CravingToolsUiModel
import com.shahzadian.cravingsscreen.ui.model.CravingsToolUiModel

@Composable
fun CravingsScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        HeaderView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 16.dp)
        )
        CravingsToolsView(
            uiModel = CravingToolsUiModel(
                tools = (1..3).map {
                    CravingsToolUiModel(
                        icon = ImageWrapper.FromRes(R.drawable.ic_cravings_main),
                        title = TextWrapper.FromString("Tool $it"),
                        subtitle = TextWrapper.FromString("Subtitle $it"),
                        onClick = {
                            Log.d("LogMog", "tool clicked")
                        }
                    )
                },
                showAllButton = true,
                onAllButtonClick = {},
            )
        )
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderIconView(
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.cravings_header_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 12.dp),
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.cravings_header_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun HeaderIconView(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "Title scale")
    val scale by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Title scale"
    )
    Image(
        painter = painterResource(R.drawable.ic_cravings_main),
        contentDescription = "header",
        modifier = modifier
            .scale(scale),
    )
}
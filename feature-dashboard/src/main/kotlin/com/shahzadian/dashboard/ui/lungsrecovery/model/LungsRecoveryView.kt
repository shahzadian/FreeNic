package com.shahzadian.dashboard.ui.lungsrecovery.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.DashboardCard
import kotlin.math.max
import kotlin.math.roundToInt

private const val ANIMATION_DURATION_PERCENTAGE: Int = 3000

@Composable
fun LungsRecoveryView(
    model: LungsRecoveryUi,
    modifier: Modifier = Modifier,
) {
    DashboardCard(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = it,
        ) {
            LungsRecoveryCircle(
                recoveryPercentage = model.recoveryPercentage,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize()
            )
            LungsRecoveryText(
                recoveryPercentage = model.recoveryPercentage,
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 24.dp)
            )
        }
    }
}

@Composable
private fun LungsRecoveryCircle(
    recoveryPercentage: Int,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "Lungs Recovery Progress")
    val scale by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Lungs Recovery Progress"
    )
    Box(
        modifier = modifier
            .border(
                border = BorderStroke(7.dp, MaterialTheme.colorScheme.primary),
                shape = CircleShape,
            )
            .width(100.dp)
            .height(100.dp)
            .scale(scale)
    ) {
        // Animated increasing number for the percentage text
        val animatedValue = remember { Animatable(0f) }
        val firstLaunch = remember { mutableStateOf(true) }
        LaunchedEffect(recoveryPercentage) {
            if (firstLaunch.value) {
                // Ensure the animation starts from 0 only on the first composition
                animatedValue.snapTo(0f)
                firstLaunch.value = false
            }
            animatedValue.animateTo(
                targetValue = recoveryPercentage.toFloat(),
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_PERCENTAGE,
                    easing = LinearOutSlowInEasing
                )
            )
        }
        val displayedPercent = if (recoveryPercentage > 0) {
            max(1, animatedValue.value.roundToInt())
        } else 0
        Text(
            text = "$displayedPercent%",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun LungsRecoveryText(
    recoveryPercentage: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.dashboard_lungs_rec_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = stringResource(R.string.dashboard_lungs_rec_subtitle, recoveryPercentage),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}
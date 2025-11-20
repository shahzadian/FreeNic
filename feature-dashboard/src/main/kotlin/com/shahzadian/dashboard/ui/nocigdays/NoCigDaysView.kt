package com.shahzadian.dashboard.ui.nocigdays

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.ANIMATION_DURATION_INCREASE_DASHBOARD
import com.shahzadian.dashboard.ui.DashboardCard
import com.shahzadian.dashboard.ui.nocigdays.model.NoCigDaysUiModel
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun NoCigDaysView(
    model: NoCigDaysUiModel,
    modifier: Modifier = Modifier,
) {
    DashboardCard(
        modifier = modifier,
    ) {
        Column(
            modifier = it,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DaysView(
                days = model.days,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 4.dp),
            )
            Text(
                text = pluralStringResource(R.plurals.dashboard_no_cig_days_title, model.days),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            PulseTextView()
        }
    }
}

@Composable
private fun DaysView(
    days: Int,
    modifier: Modifier = Modifier,
) {
    val animatedValue = remember { Animatable(0f) }
    val firstLaunch = remember { mutableStateOf(true) }
    LaunchedEffect(days) {
        if (firstLaunch.value) {
            animatedValue.snapTo(0f)
            firstLaunch.value = false
        }
        animatedValue.animateTo(
            targetValue = days.toFloat(),
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_INCREASE_DASHBOARD,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val displayText = if (days > 0) {
        max(1, animatedValue.value.roundToInt())
    } else 0
    Text(
        text = displayText.toString(),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier,
    )
}

@Composable
private fun PulseTextView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        val transition = rememberInfiniteTransition("pulse transition")
        val scale by transition.animateFloat(
            initialValue = 1f,
            targetValue = 1.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                ),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "pulse transition",
        )
        Box(
            modifier = Modifier
                .width(10.dp)
                .height(10.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(end = 6.dp)
        )
        Text(
            text = stringResource(R.string.dashboard_no_cig_days_subtitle),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 6.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
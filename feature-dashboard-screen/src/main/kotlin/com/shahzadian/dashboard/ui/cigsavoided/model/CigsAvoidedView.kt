package com.shahzadian.dashboard.ui.cigsavoided.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.dashboard.R
import com.shahzadian.dashboard.ui.ANIMATION_DURATION_INCREASE_DASHBOARD
import com.shahzadian.dashboard.ui.DashboardCard
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun CigsAvoidedView(
    model: CigsAvoidedUiModel,
    modifier: Modifier = Modifier,
) {
    DashboardCard(
        modifier = modifier,
    ) {
        Column(
            modifier = it,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_dashboard_cigs_avoided),
                contentDescription = "Cigarettes avoided",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.dashboard_cigs_avoided_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            CountView(
                count = model.count,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun CountView(
    count: Int,
    modifier: Modifier = Modifier,
) {
    val animatedValue = remember { Animatable(0f) }
    val firstLaunch = remember { mutableStateOf(true) }
    LaunchedEffect(count) {
        if (firstLaunch.value) {
            animatedValue.snapTo(0f)
            firstLaunch.value = false
        }
        animatedValue.animateTo(
            targetValue = count.toFloat(),
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_INCREASE_DASHBOARD,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val countText = if (count > 0) {
        max(1, animatedValue.value.roundToInt())
    } else 0
    Text(
        text = countText.toString(),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}
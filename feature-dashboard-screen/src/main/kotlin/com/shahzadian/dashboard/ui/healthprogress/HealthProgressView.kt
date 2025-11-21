package com.shahzadian.dashboard.ui.healthprogress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.dashboard.ui.ANIMATION_DURATION_INCREASE_DASHBOARD
import com.shahzadian.dashboard.ui.DashboardCard
import com.shahzadian.dashboard.ui.healthprogress.model.HealthProgressUiModel

@Composable
internal fun HealthProgressView(
    model: HealthProgressUiModel,
    modifier: Modifier = Modifier,
) {
    DashboardCard(
        modifier = modifier,
    ) {
        Column(
            modifier = it,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            model.items.forEach { item ->
                ProgressItem(
                    item = item,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun ProgressItem(
    item: HealthProgressUiModel.HealthProgressItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = imageFromWrapper(item.icon),
            contentDescription = textFromWrapper(item.label),
            modifier = Modifier
                .size(32.dp)
                .padding(end = 12.dp)
        )
        val target = (item.progress.coerceIn(0, 100)) / 100f
        val animatedProgress = remember { Animatable(0f) }
        LaunchedEffect(key1 = target) {
            animatedProgress.animateTo(
                targetValue = target,
                animationSpec = tween(durationMillis = ANIMATION_DURATION_INCREASE_DASHBOARD)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 6.dp).fillMaxWidth()
            ) {
                Text(
                    text = textFromWrapper(item.label),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.fillMaxWidth(0.5f).weight(1f),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "${(animatedProgress.value * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 4.dp),
                    textAlign = TextAlign.End,
                )
            }

            LinearProgressIndicator(
                progress = {
                    animatedProgress.value
                },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(MaterialTheme.shapes.small)
            )
        }
    }
}
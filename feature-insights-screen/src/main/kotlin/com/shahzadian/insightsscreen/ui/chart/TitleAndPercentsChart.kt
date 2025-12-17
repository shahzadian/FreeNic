package com.shahzadian.insightsscreen.ui.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState

@Composable
internal fun TitleAndPercentsChart(
    chart: InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = textFromWrapper(chart.title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = textFromWrapper(chart.subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
            PercentsIndicatorView(
                chartId = chart.id,
                percents = chart.percents,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        ChartBadgeView(
            badgeUiModel = chart.badge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PercentsIndicatorView(
    chartId: String,
    percents: Int,
    modifier: Modifier = Modifier,
) {
    val animatable = remember { Animatable(0f) }
    LaunchedEffect(chartId) {
        animatable.snapTo(0f)
        animatable.animateTo(
            percents.toFloat(),
            animationSpec = tween(1000)
        )
    }
    Box(
        modifier = modifier
            .size(60.dp)
    ) {
        CircularProgressIndicator(
            progress = { animatable.value / 100 },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            strokeWidth = 8.dp
        )
        Text(
            text = "${animatable.value.toInt()}%",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

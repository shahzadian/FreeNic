package com.shahzadian.insightsscreen.ui.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
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
        HeaderView(
            chart = chart,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        IndicatorView(
            chartId = chart.id,
            percents = chart.percents,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = textFromWrapper(chart.subtitle),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun HeaderView(
    chart: InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = imageFromWrapper(chart.icon),
            contentDescription = "Header image",
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = textFromWrapper(chart.title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            ChartBadgeView(
                badgeUiModel = chart.badge,
            )
        }
    }
}

@Composable
private fun IndicatorView(
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
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "${animatable.value.toInt()}%",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        LinearProgressIndicator(
            progress = { animatable.value / 100 },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

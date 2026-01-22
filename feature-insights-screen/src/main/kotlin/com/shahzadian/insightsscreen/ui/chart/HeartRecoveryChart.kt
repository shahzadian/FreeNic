package com.shahzadian.insightsscreen.ui.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.shahzadian.baseui.R
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState

@Composable
internal fun HeartRecoveryChart(
    uiModel: InsightsViewState.ChartUiModel.HeartRecoveryUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.insights_heart_recovery_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
        PercentsView(
            percents = uiModel.percents,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        HeartImageView(
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.insights_circulation_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PercentsView(
    percents: Int,
    modifier: Modifier = Modifier,
) {
    val animatable = remember { Animatable(0f) }
    LaunchedEffect(percents) {
        animatable.snapTo(0f)
        animatable.animateTo(
            percents.toFloat(),
            animationSpec = tween(1000)
        )
    }
    Text(
        text = "${animatable.value.toInt()}%",
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier,
    )
}

@Composable
private fun HeartImageView(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(com.shahzadian.insightsscreen.R.drawable.ic_insight_heart),
        contentDescription = "Lungs recovery",
        modifier = modifier,
    )
}
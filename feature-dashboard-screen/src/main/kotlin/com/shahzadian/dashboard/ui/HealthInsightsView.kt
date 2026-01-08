package com.shahzadian.dashboard.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainAnimatableLinearProgressIndicator
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.dashboard.R
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

@Composable
internal fun HealthInsightsView(
    data: DashboardViewState.HealthInsightsUiModel,
    onDetailedInsightsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onDetailedInsightsClick = remember {
        { onDetailedInsightsClicked() }
    }
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large,
            )
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_health_insights_premium),
            contentDescription = "Health insights premium",
            modifier = Modifier
                .align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            HeaderView(
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            ProgressView(
                data = data,
                onDetailedInsightsClicked = onDetailedInsightsClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_health_insights),
            contentDescription = "Health insights",
            modifier = Modifier.padding(end = 8.dp)
        )
        Column {
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.dashboard_health_insights_title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.dashboard_health_insights_subtitle),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ProgressView(
    data: DashboardViewState.HealthInsightsUiModel,
    onDetailedInsightsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onDetailedInsightsClick = remember {
        { onDetailedInsightsClicked() }
    }
    val progressAnimated = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progressAnimated.snapTo(0f)
        progressAnimated.animateTo(
            targetValue = data.progress.toFloat(),
            animationSpec = tween(
                durationMillis = 1000,
            )
        )
    }
    Column {
        Row(
            modifier = modifier
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = textFromWrapper(data.label),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "${progressAnimated.value.toInt()}%",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        MainAnimatableLinearProgressIndicator(
            progress = data.progress.toFloat(),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.medium,
                )
                .padding(16.dp)
                .clickable(onClick = onDetailedInsightsClick),
        ) {
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.dashboard_health_insights_see_details),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
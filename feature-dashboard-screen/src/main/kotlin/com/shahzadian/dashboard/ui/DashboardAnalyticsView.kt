package com.shahzadian.dashboard.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

private const val ANIMATION_DURATION: Int = 1000

@Composable
internal fun DashboardAnalyticsView(
    data: DashboardViewState.DashboardAnalyticsUiModel,
    onDetailsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val detailsClick = remember(Unit) { { onDetailsClicked() } }
    Row(
        modifier = modifier
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        NoCigDaysView(
            days = data.noCigDays,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
        )
        SavedMoneyView(
            savedMoney = data.savedMoney,
            currency = data.currency,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
        )
    }
    DetailsView(
        onClicked = detailsClick,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun NoCigDaysView(
    days: Int,
    modifier: Modifier = Modifier,
) {
    val noCigDaysAnimated = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        noCigDaysAnimated.snapTo(0f)
        noCigDaysAnimated.animateTo(
            days.toFloat(),
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION,
            )
        )
    }
    val text = pluralStringResource(R.plurals.dashboard_analytics_no_cig_days, days)
    AnalyticsCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = noCigDaysAnimated.value.toInt().toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}

@Composable
private fun SavedMoneyView(
    savedMoney: Float,
    currency: String,
    modifier: Modifier = Modifier,
) {
    val savedMoneyAnimated = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        savedMoneyAnimated.snapTo(0f)
        savedMoneyAnimated.animateTo(
            savedMoney,
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION,
            )
        )
    }
    AnalyticsCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            val displayableMoney = if (savedMoneyAnimated.value == savedMoney) {
                savedMoneyAnimated.value.toString()
            } else {
                savedMoneyAnimated.value.toInt().toString()
            }
            Text(
                text = "$displayableMoney$currency",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            Text(
                text = stringResource(R.string.dashboard_analytics_money_saved),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}

@Composable
private fun DetailsView(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val click = remember(Unit) { { onClicked() } }
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.medium,
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                click()
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(com.shahzadian.dashboard.R.drawable.ic_dashboard_analytics_premium),
            contentDescription = "premium",
            modifier = Modifier
                .padding(end = 4.dp),
        )
        Text(
            text = stringResource(R.string.dashboard_analytics_view_more),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun AnalyticsCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        content()
    }
}
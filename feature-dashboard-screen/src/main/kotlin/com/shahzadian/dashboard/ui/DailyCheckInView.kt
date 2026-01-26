package com.shahzadian.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

@Composable
internal fun DailyCheckInView(
    onEmojiSelected: (DashboardViewState.DailyCheckInResult) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        HeaderView(
            modifier = Modifier.padding(bottom = 16.dp)
        )
        EmojiBlockView(onEmojiSelected = onEmojiSelected)
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.dashboard_daily_check_in_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.dashboard_daily_check_in_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun EmojiBlockView(
    onEmojiSelected: (DashboardViewState.DailyCheckInResult) -> Unit,
    modifier: Modifier = Modifier,
) {
    val emojiSelected: (DashboardViewState.DailyCheckInResult) -> Unit =
        remember { { onEmojiSelected(it) } }
    Row(
        modifier = modifier,
    ) {
        EmojiView(
            emoji = "\uD83D\uDE1E",
            textRes = com.shahzadian.baseui.R.string.dashboard_daily_check_in_hard,
            onSelected = {
                emojiSelected(DashboardViewState.DailyCheckInResult.HARD)
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        EmojiView(
            emoji = "\uD83D\uDE10",
            textRes = com.shahzadian.baseui.R.string.dashboard_daily_check_in_okay,
            onSelected = {
                emojiSelected(DashboardViewState.DailyCheckInResult.OKAY)
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp, start = 4.dp)
        )
        EmojiView(
            emoji = "\uD83D\uDE04",
            textRes = com.shahzadian.baseui.R.string.dashboard_daily_check_in_happy,
            onSelected = {
                emojiSelected(DashboardViewState.DailyCheckInResult.HAPPY)
            },
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
}

@Composable
private fun EmojiView(
    emoji: String,
    textRes: Int,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selected = remember(emoji) { { onSelected() } }
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium,
            )
            .clickable(onClick = selected)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = emoji,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(textRes),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
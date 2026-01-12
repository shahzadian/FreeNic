package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.journeycalendarscreen.R

@Composable
fun JourneyCalendarLegendsView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            LegendView(
                iconRes = R.drawable.ic_journey_calendar_legend_smoke_free,
                labelRes = com.shahzadian.baseui.R.string.journey_calendar_legend_smoke_free,
                hasBackground = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
            LegendView(
                iconRes = R.drawable.ic_journey_calendar_event_achievement,
                labelRes = com.shahzadian.baseui.R.string.journey_calendar_legend_achievement,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
        Row {
            LegendView(
                iconRes = R.drawable.ic_journey_calendar_event_slip,
                labelRes = com.shahzadian.baseui.R.string.journey_calendar_legend_slip,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
            LegendView(
                iconRes = R.drawable.ic_journey_calendar_legend_savings,
                labelRes = com.shahzadian.baseui.R.string.journey_calendar_legend_savings,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }
}

@Composable
private fun LegendView(
    iconRes: Int,
    labelRes: Int,
    hasBackground: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(32.dp)
                .then(
                    if (hasBackground) {
                        Modifier
                            .background(
                                color = MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.small,
                            )
                            .padding(4.dp)
                    } else {
                        Modifier
                    }
                )
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

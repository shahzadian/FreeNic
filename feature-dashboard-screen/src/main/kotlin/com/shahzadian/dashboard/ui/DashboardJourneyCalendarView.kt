package com.shahzadian.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R

@Composable
internal fun DashboardJourneyCalendarView(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val click = remember(Unit) { { onClicked() } }
    Row(
        modifier = modifier
            .height(65.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
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
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(com.shahzadian.dashboard.R.drawable.ic_dashboard_journey_calendar),
            contentDescription = "premium",
            modifier = Modifier
                .padding(end = 4.dp),
        )
        Text(
            text = stringResource(R.string.dashboard_journey_calendar),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(start = 4.dp, end = 8.dp)
        )
    }
}
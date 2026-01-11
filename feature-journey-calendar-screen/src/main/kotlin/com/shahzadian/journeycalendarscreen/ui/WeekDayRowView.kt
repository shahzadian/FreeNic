package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.shahzadian.baseui.R

@Composable
internal fun WeekDayRowView(
    modifier: Modifier = Modifier,
) {
    val weekdays = listOf(
        R.string.journey_calendar_weekday_monday,
        R.string.journey_calendar_weekday_tuesday,
        R.string.journey_calendar_weekday_wednesday,
        R.string.journey_calendar_weekday_thursday,
        R.string.journey_calendar_weekday_friday,
        R.string.journey_calendar_weekday_saturday,
        R.string.journey_calendar_weekday_sunday,
    )
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        weekdays.forEach { resId ->
            Text(
                text = stringResource(resId),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
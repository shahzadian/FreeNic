package com.shahzadian.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.theme.Smoke
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.dashboard.R
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayEvent

@Composable
internal fun DashboardJourneyCalendarView(
    uiModel: DashboardViewState.CalendarUiModel,
    onViewCalendarClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val calendarClick = remember(Unit) { { onViewCalendarClicked() } }
    Column(
        modifier = modifier
            .clickable(onClick = calendarClick)
            .mainCardBackground()
            .padding(16.dp),
    ) {
        HeaderView(
            todayDate = textFromWrapper(uiModel.todayDisplayableDate),
            modifier = Modifier.padding(bottom = 16.dp),
        )
        EventView(
            model = uiModel.eventToShow,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.dashboard_calendar_view_full_button),
            onClick = calendarClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HeaderView(
    todayDate: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_calendar_header),
            contentDescription = "Calendar",
            modifier = Modifier.padding(end = 8.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
        )
        Text(
            text = stringResource(
                com.shahzadian.baseui.R.string.dashboard_calendar_header_today,
                todayDate
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun EventView(
    model: JourneyCalendarDayEvent,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = Smoke,
                shape = MaterialTheme.shapes.medium,
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = imageFromWrapper(model.icon),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(end = 8.dp)
        )
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = textFromWrapper(model.title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = model.subtitle?.let { 4.dp } ?: 0.dp)
            )
            model.subtitle?.let { subtitle ->
                Text(
                    text = textFromWrapper(subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}


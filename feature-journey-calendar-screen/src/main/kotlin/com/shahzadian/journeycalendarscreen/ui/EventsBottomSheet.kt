package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.ImageWrapper
import com.shahzadian.journeycalendarscreen.R
import com.shahzadian.journeycalendarscreen.calendarhelper.DayEventShowType
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayCell
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventsBottomSheet(
    cell: JourneyCalendarDayCell,
    onEventsSheetDismissRequested: () -> Unit,
    modifier: Modifier = Modifier
) {
    val eventsSheetDismissRequest = remember {
        { onEventsSheetDismissRequested() }
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    ModalBottomSheet(
        onDismissRequest = eventsSheetDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            EventsView(
                cell = cell,
                dismissRequest = eventsSheetDismissRequest,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun EventsView(
    cell: JourneyCalendarDayCell,
    dismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 64.dp),
    ) {
        cell.fullDayText?.let { dayText ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = textFromWrapper(dayText),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .weight(1f)
                )
                Image(
                    painter = painterResource(R.drawable.ic_journey_calendar_events_bottom_sheet_close),
                    contentDescription = "Close bottom sheet",
                    modifier = Modifier.clickable(onClick = dismissRequest)
                )
            }
            EventsView(
                cell = cell,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EventsView(
    cell: JourneyCalendarDayCell,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState),
    ) {
        cell.events.forEach { event ->
            if (event.showTypes.none { it is DayEventShowType.BottomSheet }) return@forEach
            EventView(
                model = event,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun EventView(
    model: JourneyCalendarDayEvent,
    modifier: Modifier = Modifier,
    hasDefaultBackground: Boolean = true,
) {
    Row(
        modifier = modifier
            .then(
                if (hasDefaultBackground) {
                    Modifier.mainCardBackground()
                } else {
                    Modifier
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        EventIconView(
            icon = model.icon,
            modifier = Modifier.padding(end = 8.dp)
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

@Composable
private fun EventIconView(
    icon: ImageWrapper,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape,
            )
            .size(34.dp)
            .padding(4.dp)
    ) {
        Image(
            painter = imageFromWrapper(icon),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.Center),
        )
    }
}

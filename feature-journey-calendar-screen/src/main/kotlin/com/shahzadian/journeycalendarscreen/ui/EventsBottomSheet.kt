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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.journeycalendarscreen.R
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayCellUiModel
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventsBottomSheet(
    cell: DayCellUiModel,
    onSheetDismissRequested: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissRequest = remember {
        { onSheetDismissRequested() }
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    ModalBottomSheet(
        onDismissRequest = dismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
}

@Composable
private fun EventsView(
    cell: DayCellUiModel,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState),
    ) {
        cell.noCigData?.let { noCigData ->
            NoCigDaysEventView(
                data = noCigData,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
        cell.events.forEach { event ->
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
private fun NoCigDaysEventView(
    data: DayCellUiModel.NoCigData,
    modifier: Modifier = Modifier,
) {
    val title = stringResource(com.shahzadian.baseui.R.string.journey_calendar_event_no_cig_title)
    val subtitle = pluralStringResource(
        com.shahzadian.baseui.R.plurals.journey_calendar_event_no_cig_subtitle,
        data.noCigDays,
        data.noCigDays, data.displayableSavedMoney
    )
    EventView(
        model = DayEventUiModel(
            title = TextWrapper.FromString(title),
            subtitle = TextWrapper.FromString(subtitle),
            icon = ImageWrapper.FromRes(
                R.drawable.ic_journey_calendar_event_no_cig_days
            )
        ),
        modifier = modifier,
    )
}

@Composable
private fun EventView(
    model: DayEventUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .mainCardBackground()
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
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = textFromWrapper(model.subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
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
            .size(48.dp)
            .padding(16.dp)
    ) {
        Image(
            painter = imageFromWrapper(icon),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

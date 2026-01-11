package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewEvent
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewModel
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewState

@Composable
fun JourneyCalendarScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: JourneyCalendarViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is JourneyCalendarViewState.Loading -> {}
        is JourneyCalendarViewState.Data -> ContentView(
            data = state as JourneyCalendarViewState.Data,
            onEvent = viewModel::onViewEvent,
            modifier = modifier,
        )
    }
}

@Composable
private fun ContentView(
    data: JourneyCalendarViewState.Data,
    onEvent: (JourneyCalendarViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val month = data.monthDataUiModel
    val onPrevMonthEvent = remember { { onEvent(JourneyCalendarViewEvent.OnPreviousMonthClicked) } }
    val onNextMonthEvent = remember { { onEvent(JourneyCalendarViewEvent.OnNextMonthClicked) } }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        CalendarHeaderView(
            title = month.monthTitle,
            onPrevClicked = onPrevMonthEvent,
            onNextClicked = onNextMonthEvent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        WeekDayRowView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 4.dp)
        )

        // Grid 7 columns, with fixed count to keep rows aligned
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = false,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val flat = month.weeks.flatten()
            items(items = flat) { cell ->
                DayCellView(
                    onDayClicked = {},
                    cell = cell,
                )
            }
        }
    }
}


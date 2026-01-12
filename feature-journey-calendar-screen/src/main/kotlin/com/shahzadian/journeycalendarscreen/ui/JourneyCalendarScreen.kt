package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.MainToolbarView
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.JourneyCalendarLegendsView
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewCommand
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewEvent
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewModel
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewState

@Composable
fun JourneyCalendarScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel: JourneyCalendarViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    ContentView(
        state = state,
        onEvent = viewModel::onViewEvent,
        modifier = modifier.fillMaxSize(),
    )
    LaunchedEffect(viewModel) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is JourneyCalendarViewCommand.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    state: JourneyCalendarViewState,
    onEvent: (JourneyCalendarViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp),
    ) {
        val onBackIconClickEvent =
            remember { { onEvent(JourneyCalendarViewEvent.OnBackIconClicked) } }
        MainToolbarView(
            title = TextWrapper.FromRes(R.string.journey_calendar_header),
            hasBackIcon = true,
            onBackIconClicked = onBackIconClickEvent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        if (state is JourneyCalendarViewState.Loading) {
            AppLoaderView(
                modifier = Modifier.fillMaxSize()
            )
            return
        }
        val data = state as JourneyCalendarViewState.Data
        val month = data.monthDataUiModel
        val onPrevMonthEvent =
            remember { { onEvent(JourneyCalendarViewEvent.OnPreviousMonthClicked) } }
        val onNextMonthEvent = remember { { onEvent(JourneyCalendarViewEvent.OnNextMonthClicked) } }

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

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            userScrollEnabled = false,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val flat = month.weeks.flatten()
            itemsIndexed(
                items = flat,
                key = { index, _ -> "${data.changeId}-$index" }
            ) { _, cell ->
                Box {
                    DayCellView(
                        onDayClicked = {},
                        cell = cell,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                    )
                }
            }
        }
        JourneyCalendarLegendsView(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


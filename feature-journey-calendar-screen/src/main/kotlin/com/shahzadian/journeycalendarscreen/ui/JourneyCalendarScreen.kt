package com.shahzadian.journeycalendarscreen.ui

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppLoaderView
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.MainToolbarView
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayCell
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewCommand
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewEvent
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewModel
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewState

@Composable
fun JourneyCalendarScreen(
    navigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    val viewModel: JourneyCalendarViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    ContentView(
        state = state,
        onEvent = viewModel::onViewEvent,
        modifier = modifier.fillMaxSize(),
    )
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is JourneyCalendarViewCommand.NavigateBack -> {
                        navigator.navigateBack()
                    }

                    is JourneyCalendarViewCommand.NavigateTo -> {
                        navigator.navigateToScreen(command.screen)
                    }

                    is JourneyCalendarViewCommand.NavigateToPaywallScreen -> {
                        navigator.navigateToRoute(
                            route = AppScreen.PaywallScreen.composeRoute(
                                source = command.source,
                            )
                        )
                    }

                    is JourneyCalendarViewCommand.ShowError -> {
                        Toast.makeText(
                            context,
                            context.textFromWrapper(command.message),
                            Toast.LENGTH_SHORT
                        ).show()
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
            subtitle = state.extractToolbarSubtitle()?.let {
                TextWrapper.FromString(it)
            },
            hasBackIcon = true,
            onBackIconClicked = onBackIconClickEvent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )
        if (state is JourneyCalendarViewState.Loading) {
            AppLoaderView(
                modifier = Modifier.fillMaxSize()
            )
            return
        }
        if (state is JourneyCalendarViewState.Preview) {
            val onSubscribeClickEvent =
                remember { { onEvent(JourneyCalendarViewEvent.OnCalendarPreviewSubscribeClicked) } }
            val onWatchVideoClickEvent =
                remember { { onEvent(JourneyCalendarViewEvent.OnWatchVideoClicked) } }
            CalendarPreviewView(
                rewardState = state.previewRewardState,
                onSubscribeClicked = onSubscribeClickEvent,
                onWatchVideoClicked = onWatchVideoClickEvent,
                modifier = Modifier.fillMaxSize(),
            )
            return
        }
        val data = state as JourneyCalendarViewState.Data
        val month = data.journeyCalendarMonthData
        val dayClick: (JourneyCalendarDayCell) -> Unit =
            remember { { cell -> onEvent(JourneyCalendarViewEvent.OnDayTapped(cell)) } }
        val bottomSheetDismissRequired =
            remember { { onEvent(JourneyCalendarViewEvent.OnBottomSheetDismissRequired) } }
        data.tappedDay?.let { cell ->
            EventsBottomSheet(
                cell = cell,
                onEventsSheetDismissRequested = bottomSheetDismissRequired,
                modifier = Modifier.fillMaxSize()
            )
        }
        val onPrevMonthEvent =
            remember { { onEvent(JourneyCalendarViewEvent.OnPreviousMonthClicked) } }
        val onNextMonthEvent = remember { { onEvent(JourneyCalendarViewEvent.OnNextMonthClicked) } }

        CalendarHeaderView(
            title = month.monthTitle,
            hasPreviousMonth = data.hasPreviousMonth,
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
                        onDayClicked = {
                            dayClick(cell)
                        },
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

@Composable
private fun JourneyCalendarViewState.extractToolbarSubtitle(): String? {
    val data = this as? JourneyCalendarViewState.Data ?: return null
    return when (data.calendarAccessType) {
        is JourneyCalendarViewState.CalendarAccessType.UntilTheDayEnd -> {
            stringResource(R.string.journey_calendar_header_subtitle_access_until_day_end)
        }

        else -> null
    }
}


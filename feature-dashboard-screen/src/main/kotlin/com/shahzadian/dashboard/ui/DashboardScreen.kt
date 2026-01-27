package com.shahzadian.dashboard.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.dailycheckin.DailyCheckInConfirmationView
import com.shahzadian.dashboard.ui.dailycheckin.DailyCheckInSelectionView
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewCommand
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewEvent
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewModel
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

@Composable
fun DashboardScreen(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is DashboardViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is DashboardViewState.Data -> {
            ContentView(
                data = (state as DashboardViewState.Data),
                onViewModelEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize(),
            )
        }
    }
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is DashboardViewCommand.NavigateTo -> {
                        navigator.navigateToScreen(command.screen)
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: DashboardViewState.Data,
    onViewModelEvent: (DashboardViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onWantToSmokeClick = remember {
        { onViewModelEvent(DashboardViewEvent.OnWantToSmokeClicked) }
    }
    val onISmokedClick = remember {
        { onViewModelEvent(DashboardViewEvent.OnISmokedClicked) }
    }
    val onDetailedInsightsClick = remember {
        { onViewModelEvent(DashboardViewEvent.OnDetailedInsightsClicked) }
    }
    val onJourneyCalendarClick = remember {
        { onViewModelEvent(DashboardViewEvent.OnJourneyCalendarClicked) }
    }
    val onMotivationalNoteRefreshClick = remember {
        { onViewModelEvent(DashboardViewEvent.OnMotivationalNoteRefreshClicked) }
    }
    val onDailyCheckInResultClick: (DashboardViewState.DailyCheckInResult) -> Unit = remember {
        { onViewModelEvent(DashboardViewEvent.OnDailyCheckInClicked(it)) }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.dashboard_header_title),
        subtitle = TextWrapper.FromRes(R.string.dashboard_header_subtitle),
        modifier = modifier,
    ) {
        DashboardAnalyticsView(
            data = data.analyticsData,
            onDetailsClicked = onDetailedInsightsClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
        )
        DashboardWantToSmokeView(
            onClicked = onWantToSmokeClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )
        ISmokedView(
            onClicked = onISmokedClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )
        DashboardJourneyCalendarView(
            onClicked = onJourneyCalendarClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )
        DailyCheckIn(
            uiModel = data.dailyCheckIn,
            onResult = onDailyCheckInResultClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )
        MotivationalNoteView(
            noteUiModel = data.motivationalNote,
            onClicked = onMotivationalNoteRefreshClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun DailyCheckIn(
    uiModel: DashboardViewState.DailyCheckInUiModel,
    onResult: (DashboardViewState.DailyCheckInResult) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiModel) {
        is DashboardViewState.DailyCheckInUiModel.Selection -> {
            DailyCheckInSelectionView(
                onResult = onResult,
                modifier = modifier,
            )
        }

        is DashboardViewState.DailyCheckInUiModel.Confirmation -> {
            DailyCheckInConfirmationView(
                modifier = modifier,
            )
        }

        is DashboardViewState.DailyCheckInUiModel.Hide -> {
//            Hidden
        }
    }
}

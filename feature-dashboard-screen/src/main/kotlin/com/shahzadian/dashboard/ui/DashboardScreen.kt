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
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewCommand
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewEvent
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewModel
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

@Composable
fun DashboardScreen(
    navController: NavController,
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
                        navController.navigate(command.screen.route)
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
    val onCravingsHelpClick = remember {
        { onViewModelEvent(DashboardViewEvent.OnCravingsHelpClicked) }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.dashboard_header_title),
        subtitle = TextWrapper.FromRes(R.string.dashboard_header_subtitle),
        modifier = modifier,
    ) {
        MainProgressView(
            data = data.mainProgressUiModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        CravingsView(
            onHelpClicked = onCravingsHelpClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        HealthInsightsView(
            data = data.healthInsightsUiModel,
            modifier = Modifier.padding(bottom = 16.dp),
        )
    }
}

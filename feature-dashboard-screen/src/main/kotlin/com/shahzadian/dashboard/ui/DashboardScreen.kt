package com.shahzadian.dashboard.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
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
                modifier = modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: DashboardViewState.Data,
    modifier: Modifier = Modifier,
) {
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.dashboard_header_title),
        subtitle = TextWrapper.FromRes(R.string.dashboard_header_subtitle),
        modifier = modifier,
    ) { }
}

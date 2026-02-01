package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewCommand
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewEvent
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewModel
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewState

@Composable
fun CravingSupportScreen(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    val viewModel: CravingSupportViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is CravingSupportViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is CravingSupportViewState.Data -> {
            ContentView(
                data = state as CravingSupportViewState.Data,
                onEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize()
            )
        }
    }
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel.viewCommandFlow.collect { command ->
            when (command) {
                is CravingSupportViewCommand.NavigateBack -> {
                    navigator.navigateBack()
                }

                is CravingSupportViewCommand.NavigateTo -> {
                    navigator.navigateToScreen(
                        screen = command.screen,
                    )
                }
            }
        }
    }
}

@Composable
private fun ContentView(
    data: CravingSupportViewState.Data,
    onEvent: (CravingSupportViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backIconClick = remember { { onEvent(CravingSupportViewEvent.OnBackIconClicked) } }
    val strengthSelectEvent: (CravingSupportViewState.CravingStrength) -> Unit = { strength ->
        onEvent(CravingSupportViewEvent.OnCravingStrengthSelected(strength))
    }
    val activityClick: (String) -> Unit = { id ->
        onEvent(CravingSupportViewEvent.OnActivityClicked(id))
    }
    HeaderScreen(
        title = TextWrapper.FromRes(com.shahzadian.baseui.R.string.craving_support_toolbar_title),
        hasBackIcon = true,
        onBackIconClicked = backIconClick,
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        CravingSupportHeaderView(
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        CravingSupportStrengthSelectView(
            selectedStrength = data.selectedCravingStrength,
            onStrengthSelected = strengthSelectEvent,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CravingSupportActivityView(
            activities = data.activities,
            onClicked = activityClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

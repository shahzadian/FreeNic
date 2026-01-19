package com.shahzadian.profilescreen.managesubscription

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.managesubscription.viewmodel.ManageSubscriptionViewCommand
import com.shahzadian.profilescreen.managesubscription.viewmodel.ManageSubscriptionViewEvent
import com.shahzadian.profilescreen.managesubscription.viewmodel.ManageSubscriptionViewModel
import com.shahzadian.profilescreen.managesubscription.viewmodel.ManageSubscriptionViewState

@Composable
fun ManageSubscriptionScreen(
    navigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    val viewModel: ManageSubscriptionViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is ManageSubscriptionViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is ManageSubscriptionViewState.Data -> {
            ContentView(
                data = (state as ManageSubscriptionViewState.Data),
                onEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize()
            )
        }
    }
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is ManageSubscriptionViewCommand.NavigateBack -> {
                        navigator.navigateBack()
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: ManageSubscriptionViewState.Data,
    onEvent: (ManageSubscriptionViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backIconClick = remember {
        { onEvent(ManageSubscriptionViewEvent.OnBackIconClicked) }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.manage_subscription_header_title),
        hasBackIcon = true,
        onBackIconClicked = backIconClick,
    ) { }
}
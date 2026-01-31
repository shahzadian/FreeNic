package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
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
}

@Composable
private fun ContentView(
    data: CravingSupportViewState.Data,
    onEvent: (CravingSupportViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backIconClick = remember { { onEvent(CravingSupportViewEvent.OnBackIconClicked) } }
    HeaderScreen(
        title = TextWrapper.FromRes(com.shahzadian.baseui.R.string.craving_support_header_title),
        hasBackIcon = true,
        onBackIconClicked = backIconClick,
        modifier = modifier,
    ) { }
}

package com.shahzadian.debugpanelscreen.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.debugpanelscreen.ui.viewmodel.DebugPanelViewModel
import com.shahzadian.debugpanelscreen.ui.viewmodel.DebugPanelViewState

@Composable
fun DebugPanelScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: DebugPanelViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is DebugPanelViewState.Loading -> {}
        is DebugPanelViewState.Data -> {
            ContentView(
                data = (state as DebugPanelViewState.Data),
                modifier = modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: DebugPanelViewState.Data,
    modifier: Modifier = Modifier,
) {
    HeaderScreen(
        title = TextWrapper.FromString("Debug Panel"),
        subtitle = TextWrapper.FromString("Set debug options"),
        modifier = modifier,
    ) {

    }
}

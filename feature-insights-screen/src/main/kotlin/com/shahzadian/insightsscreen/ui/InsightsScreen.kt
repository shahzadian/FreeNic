package com.shahzadian.insightsscreen.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewModel
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState

@Composable
fun InsightsScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: InsightsViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is InsightsViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize(),
            )
        }

        is InsightsViewState.Data -> {
            HeaderScreen(
                title = TextWrapper.FromRes(R.string.insights_header_title),
                subtitle = TextWrapper.FromRes(R.string.insights_header_subtitle),
                icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insights_header),
                modifier = modifier,
            ) {
                ContentView(
                    data = (state as InsightsViewState.Data),
                    modifier = modifier.fillMaxSize(),
                )
            }
        }
    }

}

@Composable
private fun ContentView(
    data: InsightsViewState.Data,
    modifier: Modifier = Modifier,
) {

}

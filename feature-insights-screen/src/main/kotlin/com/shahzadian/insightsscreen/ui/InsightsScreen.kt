package com.shahzadian.insightsscreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.insightsscreen.ui.chart.ChartTabView
import com.shahzadian.insightsscreen.ui.chart.DisclaimerView
import com.shahzadian.insightsscreen.ui.chart.LungsRecoveryChart
import com.shahzadian.insightsscreen.ui.chart.TitleAndPercentsChart
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewModel
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState

@Composable
fun InsightsScreen(
    navController: NavController,
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
                hasBackIcon = true,
                onBackIconClicked = {
                    navController.popBackStack()
                },
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
    var selectedChartTabId by remember { mutableStateOf(data.selectedBlockId) }
    Column(
        modifier = modifier,
    ) {
        ChartBlockTabsView(
            blocks = data.chartBlocks,
            selectedBlockId = data.selectedBlockId,
            onChartSelected = { id ->
                selectedChartTabId = id
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ChartsView(
            charts = data.chartBlocks.firstOrNull { it.id == selectedChartTabId }?.charts
                ?: throw IllegalStateException("No charts found for selected block id: $selectedChartTabId"),
            modifier = Modifier.padding(bottom = 16.dp),
        )
        DisclaimerView(
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }

}

@Composable
private fun ChartBlockTabsView(
    blocks: List<InsightsViewState.ChartBlockUiModel>,
    selectedBlockId: String,
    onChartSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val chunkedBlocks = blocks.chunked(2)
    var selectedChartTabId: String? by remember { mutableStateOf(selectedBlockId) }
    val chartClick = remember {
        { onChartSelected(requireNotNull(selectedChartTabId)) }
    }
    Column(
        modifier = modifier,
    ) {
        chunkedBlocks.forEachIndexed { index, blocks ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (index == chunkedBlocks.lastIndex) 0.dp else 8.dp)
            ) {
                blocks.forEachIndexed { blockIndex, block ->
                    ChartTabView(
                        title = block.title,
                        icon = block.icon,
                        isSelected = selectedChartTabId == block.id,
                        modifier = Modifier
                            .padding(
                                end = if (blockIndex == blocks.lastIndex) 0.dp else 2.dp,
                                start = if (blockIndex == 0) 0.dp else 2.dp,
                            )
                            .weight(1f)
                            .clickable {
                                selectedChartTabId = block.id
                                chartClick()
                            }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChartsView(
    charts: List<InsightsViewState.ChartUiModel>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        charts.forEach { chart ->
            when (chart) {
                is InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel -> {
                    TitleAndPercentsChart(
                        chart = chart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
                is InsightsViewState.ChartUiModel.LungsRecoveryUiModel -> {
                    LungsRecoveryChart(
                        uiModel = chart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                    )
                }
            }
        }
    }
}

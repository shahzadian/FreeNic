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
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.insightsscreen.ui.chart.ChartTabView
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
    ChartBlocksView(
        blocks = data.chartBlocks,
        selectedBlockId = data.selectedBlockId,
        modifier = modifier,
    )
}

@Composable
private fun ChartBlocksView(
    blocks: List<InsightsViewState.ChartBlockUiModel>,
    selectedBlockId: String,
    modifier: Modifier = Modifier,
) {
    val chunkedBlocks = blocks.chunked(2)
    var selectedChartTabId: String? by remember { mutableStateOf(selectedBlockId) }
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
                        isSelected = selectedChartTabId == block.id,
                        modifier = Modifier
                            .padding(
                                end = if (blockIndex == blocks.lastIndex) 0.dp else 2.dp,
                                start = if (blockIndex == 0) 0.dp else 2.dp,
                            )
                            .weight(1f)
                            .clickable {
                                selectedChartTabId = block.id
                            }
                    )
                }
            }
        }
    }
}

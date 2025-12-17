package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.insightsscreen.domain.GetInsightChartsBlocksUseCase
import com.shahzadian.insightsscreen.ui.InsightChartMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class InsightsViewModel @Inject constructor(
    private val getInsightChartsBlocksUseCase: GetInsightChartsBlocksUseCase,
    private val insightChartMapper: InsightChartMapper,
) : BaseViewModel<InsightsViewState, InsightsViewEvent, InsightsViewCommand>() {

    override val initState: InsightsViewState by lazy { InsightsViewState.Loading }

    init {
        setData()
    }

    override fun onViewEventHappened(event: InsightsViewEvent) {

    }

    private fun setData() {
        viewModelScope.launch {
            val chartBlocks = getInsightChartsBlocksUseCase()
                .map { insightChartMapper.map(it) }
            if (chartBlocks.isNotEmpty()) {
                setViewState(
                    InsightsViewState.Data(
                        chartBlocks = chartBlocks,
                        selectedBlockId = chartBlocks.first().id
                    )
                )
            }
        }
    }
}

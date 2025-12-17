package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class InsightsViewModel @Inject constructor() :
    BaseViewModel<InsightsViewState, InsightsViewEvent, InsightsViewCommand>() {

    override val initState: InsightsViewState by lazy { InsightsViewState.Loading }

    init {
        setData()
    }

    override fun onViewEventHappened(event: InsightsViewEvent) {

    }

    private fun setData() {
        viewModelScope.launch {
            setViewState(
                InsightsViewState.Data(
                    selectedBlockId = "chartBlock1",
                    chartBlocks = listOf(
                        InsightsViewState.ChartBlockUiModel(
                            id = "chartBlock1",
                            title = TextWrapper.FromString("some chart 1"),
                            charts = listOf(
                                InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel(
                                    id = "chart1",
                                    title = TextWrapper.FromString("title and percents 1"),
                                    subtitle = TextWrapper.FromString("subtitle 1"),
                                    percents = 60f,
                                    hintUiModel = InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel.HintUiModel.MedicalResearch
                                )
                            )
                        ),
                        InsightsViewState.ChartBlockUiModel(
                            id = "chartBlock2",
                            title = TextWrapper.FromString("some chart 2"),
                            charts = listOf(
                                InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel(
                                    id = "chart2",
                                    title = TextWrapper.FromString("title and percents 2"),
                                    subtitle = TextWrapper.FromString("subtitle 2"),
                                    percents = 40f,
                                    hintUiModel = InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel.HintUiModel.MedicalResearch
                                )
                            )
                        ),
                        InsightsViewState.ChartBlockUiModel(
                            id = "chartBlock3",
                            title = TextWrapper.FromString("some chart 3"),
                            charts = listOf(
                                InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel(
                                    id = "chart3",
                                    title = TextWrapper.FromString("title and percents 3"),
                                    subtitle = TextWrapper.FromString("subtitle 3"),
                                    percents = 20f,
                                    hintUiModel = InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel.HintUiModel.MedicalResearch
                                )
                            )
                        )
                    )
                )
            )
        }
    }
}

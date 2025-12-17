package com.shahzadian.insightsscreen.ui

import com.shahzadian.insightsscreen.domain.InsightChart
import com.shahzadian.insightsscreen.domain.InsightChartsBlock
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState
import javax.inject.Inject

internal interface InsightChartMapper {

    fun map(insightChartsBlock: InsightChartsBlock): InsightsViewState.ChartBlockUiModel
}

internal class InsightChartMapperImpl @Inject constructor() : InsightChartMapper {

    override fun map(insightChartsBlock: InsightChartsBlock): InsightsViewState.ChartBlockUiModel {
        return InsightsViewState.ChartBlockUiModel(
            id = insightChartsBlock.id,
            title = insightChartsBlock.title,
            charts = insightChartsBlock.charts
                .map { chart ->
                    chart.toUiModel()
                }
        )
    }

    private fun InsightChart.toUiModel(): InsightsViewState.ChartUiModel {
        return when (this) {
            is InsightChart.TitleAndPercentsChart -> InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel(
                id = this.id,
                title = this.title,
                subtitle = this.description,
                icon = this.icon,
                percents = this.percents,
                badge = InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel.BadgeUiModel(
                    text = this.badge.text
                )
            )
        }
    }
}
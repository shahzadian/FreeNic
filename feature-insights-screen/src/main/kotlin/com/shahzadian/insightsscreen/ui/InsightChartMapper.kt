package com.shahzadian.insightsscreen.ui

import android.content.Context
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.insights.domain.InsightChart
import com.shahzadian.insights.domain.InsightChartBadge
import com.shahzadian.insights.domain.InsightChartsBlock
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState.ChartUiModel.HeartRecoveryUiModel
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState.ChartUiModel.LungsRecoveryUiModel
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState.ChartUiModel.SlipCasesChartUiModel
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState.ChartUiModel.SlipCasesChartUiModel.ChartItem
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel.BadgeUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal interface InsightChartMapper {

    fun map(insightChartsBlock: InsightChartsBlock): InsightsViewState.ChartBlockUiModel
}

internal class InsightChartMapperImpl @Inject constructor(
    @param:ApplicationContext
    private val context: Context,
) : InsightChartMapper {

    override fun map(insightChartsBlock: InsightChartsBlock): InsightsViewState.ChartBlockUiModel {
        return InsightsViewState.ChartBlockUiModel(
            id = insightChartsBlock.id,
            title = insightChartsBlock.title,
            icon = insightChartsBlock.icon,
            isScrollable = insightChartsBlock.charts.none { it is InsightChart.SingleChart },
            shouldShowDisclaimer = insightChartsBlock.charts.any { it is InsightChart.AchievableChart && it.badge is InsightChartBadge.MedicalEvidence },
            charts = insightChartsBlock.charts
                .map { chart ->
                    chart.toUiModel()
                }
        )
    }

    private fun InsightChart.toUiModel(): InsightsViewState.ChartUiModel {
        return when (this) {
            is InsightChart.TitleAndPercentsChart -> TitleAndPercentsChartUiModel(
                id = this.id,
                title = this.title,
                subtitle = this.description,
                icon = this.icon,
                percents = this.percents,
                badge = BadgeUiModel(
                    text = this.badge.text
                )
            )

            is InsightChart.LungsRecoveryChart -> {
                LungsRecoveryUiModel(
                    percents = this.currentPercents,
                )
            }

            is InsightChart.HeartRecoveryChart -> {
                HeartRecoveryUiModel(
                    percents = this.currentPercents,
                )
            }

            is InsightChart.SlipCasesChart -> {
                SlipCasesChartUiModel(
                    items = this.items.map {
                        ChartItem(
                            label = context.textFromWrapper(it.label),
                            fixedSlipsCount = it.fixedSlipsCount,
                            fixedCravingsCount = it.fixedCravingsCount,
                        )
                    }.toImmutableList()
                )
            }

            else -> throw IllegalArgumentException("Unknown chart type: ${this::class.simpleName}")
        }
    }
}
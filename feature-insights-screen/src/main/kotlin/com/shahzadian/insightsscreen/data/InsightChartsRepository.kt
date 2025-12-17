package com.shahzadian.insightsscreen.data

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.insightsscreen.domain.InsightChart
import com.shahzadian.insightsscreen.domain.InsightChartBadge
import com.shahzadian.insightsscreen.domain.InsightChartsBlock
import com.shahzadian.recoverycalculator.domain.HealthRecoveryCalculator

internal interface InsightChartsRepository {

    suspend fun getAllBlocks(): List<InsightChartsBlock>

    companion object {
        operator fun invoke(recoveryCalculator: HealthRecoveryCalculator): InsightChartsRepository =
            InsightChartsRepositoryImpl(recoveryCalculator)
    }
}

internal class InsightChartsRepositoryImpl(
    private val recoveryCalculator: HealthRecoveryCalculator,
) : InsightChartsRepository {

    override suspend fun getAllBlocks(): List<InsightChartsBlock> {
        return listOf(
            getMedicalBlock(),
            getHealthProgressBlock(),
            getLongTermRisksBlock(),
//            TODO premium
        )
    }

    private fun getMedicalBlock(): InsightChartsBlock {
        return InsightChartsBlock(
            id = "Medical",
            title = TextWrapper.FromRes(R.string.insights_chart_medical),
            icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insights_tab_medical),
            charts = listOf(
                InsightChart.TitleAndPercentsChart(
                    id = "Oxygen & CO normalization",
                    title = TextWrapper.FromRes(R.string.insights_oxygen_title),
                    description = TextWrapper.FromRes(R.string.insights_oxygen_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_oxygen),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateOxygenCoNormalizationPercent()
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Circulation & heart recovery",
                    title = TextWrapper.FromRes(R.string.insights_circulation_title),
                    description = TextWrapper.FromRes(R.string.insights_circulation_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_heart_recovery),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateCirculationHeartRecoveryPercent()
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Lung self-cleaning",
                    title = TextWrapper.FromRes(R.string.insights_lung_self_cleaning_title),
                    description = TextWrapper.FromRes(R.string.insights_lung_self_cleaning_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insights_chart_lung_self_cleaning),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateLungSelfCleaningPercent()
                ),
            )
        )
    }

    private fun getHealthProgressBlock(): InsightChartsBlock {
        return InsightChartsBlock(
            id = "Health progress",
            title = TextWrapper.FromRes(R.string.insights_chart_health),
            icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insights_tab_health),
            charts = listOf(
                InsightChart.TitleAndPercentsChart(
                    id = "Energy level",
                    title = TextWrapper.FromRes(R.string.insights_energy_level_title),
                    description = TextWrapper.FromRes(R.string.insights_energy_level_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_energy_level),
                    badge = InsightChartBadge.Estimated,
                    percents = recoveryCalculator.calculateEnergyLevelPercent(),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Breathing comfort",
                    title = TextWrapper.FromRes(R.string.insights_breathing_comfort_title),
                    description = TextWrapper.FromRes(R.string.insights_breathing_comfort_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_breathing_comfort),
                    badge = InsightChartBadge.Estimated,
                    percents = recoveryCalculator.calculateBreathingComfortPercent(),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Physical endurance",
                    title = TextWrapper.FromRes(R.string.insights_endurance_title),
                    description = TextWrapper.FromRes(R.string.insights_endurance_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_endurance),
                    badge = InsightChartBadge.Estimated,
                    percents = recoveryCalculator.calculatePhysicalEndurancePercent(),
                )
            )
        )
    }

    private fun getLongTermRisksBlock(): InsightChartsBlock {
        return InsightChartsBlock(
            id = "Long-term risks",
            title = TextWrapper.FromRes(R.string.insights_chart_long_term_risk),
            icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insights_tab_long_term),
            charts = listOf(
                InsightChart.TitleAndPercentsChart(
                    id = "Long-term risk reduction",
                    title = TextWrapper.FromRes(R.string.insights_long_term_risk_reduction_title),
                    description = TextWrapper.FromRes(R.string.insights_long_term_risk_reduction_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_long_term_risk_reduction),
                    badge = InsightChartBadge.Who,
                    percents = recoveryCalculator.calculateLongTermRiskReductionPercent(),
                )
            )
        )
    }
}

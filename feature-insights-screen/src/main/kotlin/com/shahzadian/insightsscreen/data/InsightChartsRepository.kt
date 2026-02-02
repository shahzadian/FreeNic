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
            getSlipCasesBlock(),
            getHealthProgressBlock(),
        )
    }

    private fun getSlipCasesBlock(): InsightChartsBlock {
        return InsightChartsBlock(
            id = "Slip cases",
            title = TextWrapper.FromRes(R.string.insights_chart_slips),
            icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insights_tab_slips),
            charts = listOf(
                InsightChart.SlipCasesChart(
                    id = "Slip cases",
                    items = listOf(
                        InsightChart.SlipCasesChart.Item(
                            label = TextWrapper.FromString("Mon"),
                            value = 2,
                        ),
                        InsightChart.SlipCasesChart.Item(
                            label = TextWrapper.FromString("Tue"),
                            value = 4,
                        ),
                        InsightChart.SlipCasesChart.Item(
                            label = TextWrapper.FromString("Wed"),
                            value = 1,
                        ),
                        InsightChart.SlipCasesChart.Item(
                            label = TextWrapper.FromString("Thu"),
                            value = 0,
                        ),
                        InsightChart.SlipCasesChart.Item(
                            label = TextWrapper.FromString("Fri"),
                            value = 5,
                        ),
                        InsightChart.SlipCasesChart.Item(
                            label = TextWrapper.FromString("Sat"),
                            value = 7,
                        ),
                        InsightChart.SlipCasesChart.Item(
                            label = TextWrapper.FromString("Sun"),
                            value = 2,
                        )
                    )
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
                InsightChart.LungsRecoveryChart(
                    id = "Lung self-cleaning",
                    title = TextWrapper.FromRes(R.string.insights_lung_self_cleaning_title),
                    description = TextWrapper.FromRes(R.string.insights_lung_self_cleaning_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insights_chart_lung_self_cleaning),
                    badge = InsightChartBadge.MedicalEvidence,
                    currentPercents = recoveryCalculator.calculateLungSelfCleaningPercent(),
                    achieveDateMillis = recoveryCalculator.calculateLungSelfCleaningDateMillisForPercents(99),
                ),
                InsightChart.HeartRecoveryChart(
                    id = "Circulation & heart recovery",
                    title = TextWrapper.FromRes(R.string.insights_circulation_title),
                    description = TextWrapper.FromRes(R.string.insights_circulation_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_heart_recovery),
                    badge = InsightChartBadge.MedicalEvidence,
                    currentPercents = recoveryCalculator.calculateCirculationHeartRecoveryPercent(),
                    achieveDateMillis = recoveryCalculator.calculateCancerRiskReductionDateMillisForPercents(99),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Oxygen & CO normalization",
                    title = TextWrapper.FromRes(R.string.insights_oxygen_title),
                    description = TextWrapper.FromRes(R.string.insights_oxygen_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_oxygen),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateOxygenCoNormalizationPercent(),
                    achieveDateMillis = recoveryCalculator.calculateOxygenCoNormalizationDateMillisForPercents(99),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Blood pressure stabilization",
                    title = TextWrapper.FromRes(R.string.insights_blood_pressure_title),
                    description = TextWrapper.FromRes(R.string.insights_blood_pressure_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_blood_pressure),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateBloodPressureStabilizationPercent(),
                    achieveDateMillis = recoveryCalculator.calculateBloodPressureDateMillisForPercents(
                        99
                    ),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Immune system recovery",
                    title = TextWrapper.FromRes(R.string.insights_immune_recovery_title),
                    description = TextWrapper.FromRes(R.string.insights_immune_recovery_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_immune_recovery),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateImmuneRecoveryPercent(),
                    achieveDateMillis = recoveryCalculator.calculateImmuneRecoveryDateMillisForPercents(
                        99
                    ),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Sleep quality improvement",
                    title = TextWrapper.FromRes(R.string.insights_sleep_quality_title),
                    description = TextWrapper.FromRes(R.string.insights_sleep_quality_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_sleep_quality),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateSleepQualityPercent(),
                    achieveDateMillis = recoveryCalculator.calculateSleepQualityDateMillisForPercents(
                        99
                    ),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Taste and smell recovery",
                    title = TextWrapper.FromRes(R.string.insights_taste_and_smell_title),
                    description = TextWrapper.FromRes(R.string.insights_taste_and_smell_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_smell_recovery),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateTasteAndSmellRecoveryPercent(),
                    achieveDateMillis = recoveryCalculator.calculateSleepQualityDateMillisForPercents(
                        99
                    ),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Stroke risk reduction",
                    title = TextWrapper.FromRes(R.string.insights_stroke_title),
                    description = TextWrapper.FromRes(R.string.insights_stroke_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_stroke),
                    badge = InsightChartBadge.Who,
                    percents = recoveryCalculator.calculateStrokeRiskReductionPercent(),
                    achieveDateMillis = recoveryCalculator.calculateStrokeRiskReductionDateMillisForPercents(99),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Cancer risk reduction",
                    title = TextWrapper.FromRes(R.string.insights_cancer_title),
                    description = TextWrapper.FromRes(R.string.insights_cancer_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_cancer),
                    badge = InsightChartBadge.Who,
                    percents = recoveryCalculator.calculateCancerRiskReductionPercent(),
                    achieveDateMillis = recoveryCalculator.calculateCancerRiskReductionDateMillisForPercents(99),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Long-term risk reduction",
                    title = TextWrapper.FromRes(R.string.insights_long_term_risk_reduction_title),
                    description = TextWrapper.FromRes(R.string.insights_long_term_risk_reduction_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_long_term_risk_reduction),
                    badge = InsightChartBadge.Who,
                    percents = recoveryCalculator.calculateLongTermRiskReductionPercent(),
                    achieveDateMillis = recoveryCalculator.calculateLongTermRiskReductionDateMillisForPercents(99),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Energy level",
                    title = TextWrapper.FromRes(R.string.insights_energy_level_title),
                    description = TextWrapper.FromRes(R.string.insights_energy_level_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_energy_level),
                    badge = InsightChartBadge.Estimated,
                    percents = recoveryCalculator.calculateEnergyLevelPercent(),
                    achieveDateMillis = recoveryCalculator.calculateEnergyLevelDateMillisForPercents(99),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Breathing comfort",
                    title = TextWrapper.FromRes(R.string.insights_breathing_comfort_title),
                    description = TextWrapper.FromRes(R.string.insights_breathing_comfort_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_breathing_comfort),
                    badge = InsightChartBadge.Estimated,
                    percents = recoveryCalculator.calculateBreathingComfortPercent(),
                    achieveDateMillis = recoveryCalculator.calculateBreathingComfortDateMillisForPercents(99),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Physical endurance",
                    title = TextWrapper.FromRes(R.string.insights_endurance_title),
                    description = TextWrapper.FromRes(R.string.insights_endurance_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_chart_endurance),
                    badge = InsightChartBadge.Estimated,
                    percents = recoveryCalculator.calculatePhysicalEndurancePercent(),
                    achieveDateMillis = recoveryCalculator.calculatePhysicalEnduranceDateMillisForPercents(99),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Stress regulation",
                    title = TextWrapper.FromRes(R.string.insights_stress_regulation_title),
                    description = TextWrapper.FromRes(R.string.insights_stress_regulation_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_stress_regulation),
                    badge = InsightChartBadge.MedicalEvidence,
                    percents = recoveryCalculator.calculateStressRegulationPercent(),
                    achieveDateMillis = recoveryCalculator.calculateStressRegulationDateMillisForPercents(
                        99
                    ),
                ),
                InsightChart.TitleAndPercentsChart(
                    id = "Skin improvement",
                    title = TextWrapper.FromRes(R.string.insights_skin_title),
                    description = TextWrapper.FromRes(R.string.insights_skin_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insight_skin_improvement),
                    badge = InsightChartBadge.Estimated,
                    percents = recoveryCalculator.calculateSkinImprovementPercent(),
                    achieveDateMillis = recoveryCalculator.calculateSkinImprovementDateMillisForPercents(
                        99
                    ),
                )
            )
        )
    }
}

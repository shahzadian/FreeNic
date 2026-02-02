package com.shahzadian.insights.data

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.cravings.cravingcase.domain.GetCravingCasesUseCase
import com.shahzadian.insights.domain.InsightChart
import com.shahzadian.slip.slipcase.domain.GetSlipCasesUseCase
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

internal interface SlipCaseChartHelper {

    suspend fun getCharts(params: InsightChart.Params): List<InsightChart.SlipCasesChart.Item>
}

internal class SlipCaseChartHelperImpl @Inject constructor(
    private val getSlipCasesUseCase: GetSlipCasesUseCase,
    private val getCravingCasesUseCase: GetCravingCasesUseCase,
) : SlipCaseChartHelper {

    override suspend fun getCharts(params: InsightChart.Params): List<InsightChart.SlipCasesChart.Item> {
        return when (params.chartPeriod) {
            is InsightChart.InsightChartPeriod.Week -> getWeekItems()
        }
    }

    private suspend fun getWeekItems(): List<InsightChart.SlipCasesChart.Item> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)
        val startDate = today.minusDays(6)

        // Получаем все с кейсами заранее
        val slipCases = getSlipCasesUseCase()
        val cravingCases = getCravingCasesUseCase()

        // Группируем по LocalDate для быстрого доступа
        val slipsByDate: Map<LocalDate, Int> = slipCases
            .groupingBy { Instant.ofEpochMilli(it.slipDateMillis).atZone(zone).toLocalDate() }
            .eachCount()

        val cravingsByDate: Map<LocalDate, Int> = cravingCases
            .groupingBy { Instant.ofEpochMilli(it.caseMillis).atZone(zone).toLocalDate() }
            .eachCount()

        // Формируем 7 элементов от старого дня к сегодняшнему
        return (0..6).map { offset ->
            val date = startDate.plusDays(offset.toLong())
            val dayOfWeek = date.dayOfWeek
            InsightChart.SlipCasesChart.Item(
                label = TextWrapper.FromRes(getWeekdayRes(dayOfWeek)),
                fixedSlipsCount = slipsByDate[date] ?: 0,
                fixedCravingsCount = cravingsByDate[date] ?: 0,
            )
        }
    }

    private fun getWeekdayRes(dayOfWeek: DayOfWeek): Int = when (dayOfWeek) {
        DayOfWeek.MONDAY -> R.string.insights_charts_slip_weekday_monday
        DayOfWeek.TUESDAY -> R.string.insights_charts_slip_weekday_tuesday
        DayOfWeek.WEDNESDAY -> R.string.insights_charts_slip_weekday_wednesday
        DayOfWeek.THURSDAY -> R.string.insights_charts_slip_weekday_thursday
        DayOfWeek.FRIDAY -> R.string.insights_charts_slip_weekday_friday
        DayOfWeek.SATURDAY -> R.string.insights_charts_slip_weekday_saturday
        DayOfWeek.SUNDAY -> R.string.insights_charts_slip_weekday_sunday
    }
}

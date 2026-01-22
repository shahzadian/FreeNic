package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.shahzadian.insightsscreen.domain.GetInsightChartsBlocksUseCase
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import java.time.LocalDate

internal class InsightChartsEventFinder(
    private val getInsightChartsBlocksUseCase: GetInsightChartsBlocksUseCase,
) : JourneyCalendarEventsFinder() {

    override suspend fun getEventsForDate(date: LocalDate): List<DayEventUiModel> {
        return getInsightChartsBlocksUseCase()
            .flatMap { chartsBlock ->
                chartsBlock
                    .charts
                    .filter { chart ->
                        val startOfDay = date.startOfDayMillis()
                        val endOfDay = date.endOfDayMillis()
                        chart.achieveDateMillis in startOfDay..endOfDay
                    }
                    .map {
                        DayEventUiModel(
                            title = it.title,
                            subtitle = it.description,
                            icon = it.icon
                        )
                    }
            }
    }
}
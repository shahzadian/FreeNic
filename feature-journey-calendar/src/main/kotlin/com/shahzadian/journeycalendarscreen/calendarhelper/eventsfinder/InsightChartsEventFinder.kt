package com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder

import android.content.Context
import com.freenic.ui.extensions.extract
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.insights.domain.GetInsightChartsBlocksUseCase
import com.shahzadian.insights.domain.InsightChart
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayEvent
import java.time.LocalDate

internal class InsightChartsEventFinder(
    private val context: Context,
    private val getInsightChartsBlocksUseCase: GetInsightChartsBlocksUseCase,
) : JourneyCalendarEventsFinder() {

    override suspend fun getEventsForDate(date: LocalDate): List<JourneyCalendarDayEvent> {
        return getInsightChartsBlocksUseCase()
            .flatMap { chartsBlock ->
                chartsBlock
                    .charts
                    .filterIsInstance<InsightChart.AchievableChart>()
                    .filter { chart ->
                        val startOfDay = date.startOfDayMillis()
                        val endOfDay = date.endOfDayMillis()
                        chart.achieveDateMillis in startOfDay..endOfDay
                    }
                    .map {
                        val title = it.title.extract(context)
                        JourneyCalendarDayEvent(
                            title = TextWrapper.FromRes(
                                R.string.journey_calendar_event_insight_title,
                                arrayListOf(title)
                            ),
                            subtitle = it.description,
                            icon = it.icon
                        )
                    }
            }
    }
}
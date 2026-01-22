package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import android.content.Context
import com.freenic.ui.extensions.extract
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.insightsscreen.domain.GetInsightChartsBlocksUseCase
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import java.time.LocalDate

internal class InsightChartsEventFinder(
    private val context: Context,
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
                        val title = it.title.extract(context)
                        DayEventUiModel(
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
package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import com.shahzadian.slip.slipcase.domain.GetSlipCasesUseCase
import com.shahzadian.slip.slipcase.domain.SlipCase
import java.time.LocalDate

internal class SlipEventsFinder(
    private val getSlipCasesUseCase: GetSlipCasesUseCase,
) : JourneyCalendarEventsFinder() {

    private var _slipCases: List<SlipCase>? = null

    override suspend fun getEventsForDate(date: LocalDate): List<DayEventUiModel> {
        val startOfDay = date.startOfDayMillis()
        val slipCases = getSlipCases()
        val endOfDay = date.endOfDayMillis()
        val casesInDay = slipCases
            .mapNotNull { case ->
                if (case.slipDateMillis in startOfDay..endOfDay) {
                    DayEventUiModel(
                        title = TextWrapper.FromRes(R.string.journey_calendar_event_slip_title),
                        subtitle = TextWrapper.FromRes(R.string.journey_calendar_event_slip_subtitle),
                        icon = ImageWrapper.FromRes(com.shahzadian.journeycalendarscreen.R.drawable.ic_journey_calendar_event_slip)
                    )
                } else {
                    null
                }
            }
        return casesInDay
    }

    private suspend fun getSlipCases(): List<SlipCase> {
        return _slipCases ?: let {
            val cases = getSlipCasesUseCase()
            _slipCases = cases
            cases
        }
    }
}

package com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.dailycheckin.domain.DailyCheckInMood
import com.shahzadian.dailycheckin.domain.GetDailyCheckInForDayUseCase
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayEvent
import java.time.LocalDate

internal class DailyCheckInEventFinder(
    private val getDailyCheckInForDayUseCase: GetDailyCheckInForDayUseCase,
) : JourneyCalendarEventsFinder() {

    override suspend fun getEventsForDate(date: LocalDate): List<JourneyCalendarDayEvent> {
        val checkInState = getDailyCheckInForDayUseCase(date)
            ?: return emptyList()
        return listOf(
            JourneyCalendarDayEvent(
                title = checkInState.mood.getTitle(),
                subtitle = TextWrapper.FromRes(R.string.calendar_event_daily_check_in_desc),
                icon = checkInState.mood.getIcon(),
            )
        )
    }

    private fun DailyCheckInMood.getTitle(): TextWrapper {
        return when (this) {
            DailyCheckInMood.HARD -> TextWrapper.FromRes(R.string.calendar_event_daily_check_in_hard)
            DailyCheckInMood.OKAY -> TextWrapper.FromRes(R.string.calendar_event_daily_check_in_okay)
            DailyCheckInMood.HAPPY -> TextWrapper.FromRes(R.string.calendar_event_daily_check_in_happy)
        }
    }
}
package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.dailycheckin.domain.DailyCheckInMood
import com.shahzadian.dailycheckin.domain.GetDailyCheckInForDayUseCase
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import java.time.LocalDate

internal class DailyCheckInEventFinder(
    private val getDailyCheckInForDayUseCase: GetDailyCheckInForDayUseCase,
) : JourneyCalendarEventsFinder() {

    override suspend fun getEventsForDate(date: LocalDate): List<DayEventUiModel> {
        val checkInState = getDailyCheckInForDayUseCase(date)
            ?: return emptyList()
        return listOf(
            DayEventUiModel(
                title = checkInState.mood.getTitle(),
                subtitle = TextWrapper.FromRes(com.shahzadian.baseui.R.string.calendar_event_daily_check_in_desc),
                icon = checkInState.mood.getIcon(),
            )
        )
    }

    private fun DailyCheckInMood.getTitle(): TextWrapper {
        return when (this) {
            DailyCheckInMood.HARD -> TextWrapper.FromRes(com.shahzadian.baseui.R.string.calendar_event_daily_check_in_hard)
            DailyCheckInMood.OKAY -> TextWrapper.FromRes(com.shahzadian.baseui.R.string.calendar_event_daily_check_in_okay)
            DailyCheckInMood.HAPPY -> TextWrapper.FromRes(com.shahzadian.baseui.R.string.calendar_event_daily_check_in_happy)
        }
    }
}
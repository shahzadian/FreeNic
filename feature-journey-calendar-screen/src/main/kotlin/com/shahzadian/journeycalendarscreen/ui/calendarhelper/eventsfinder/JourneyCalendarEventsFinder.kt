package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import java.time.LocalDate
import java.time.ZoneId

internal abstract class JourneyCalendarEventsFinder {

    protected val zone = ZoneId.systemDefault()

    abstract suspend fun findEventsForDate(date: LocalDate): List<DayEventUiModel>

    protected fun LocalDate.startOfDayMillis(): Long {
        return this
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()
    }

    protected fun LocalDate.endOfDayMillis(): Long {
        return this
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()
    }
}
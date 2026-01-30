package com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder

import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayEvent
import java.time.LocalDate
import java.time.ZoneId

internal abstract class JourneyCalendarEventsFinder {

    protected val zone: ZoneId = ZoneId.systemDefault()

    suspend fun findEventsForDate(date: LocalDate): List<JourneyCalendarDayEvent> {
        return getEventsForDate(date)
    }

    protected abstract suspend fun getEventsForDate(date: LocalDate): List<JourneyCalendarDayEvent>

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

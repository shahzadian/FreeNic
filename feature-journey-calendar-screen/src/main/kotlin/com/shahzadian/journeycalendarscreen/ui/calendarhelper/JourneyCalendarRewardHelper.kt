package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import com.freenic.cache.AppCache
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

internal interface JourneyCalendarRewardHelper {

    suspend fun onRewarded()

    suspend fun hasAccessToCalendar(): Boolean
}

internal class JourneyCalendarRewardHelperImpl @Inject constructor(
    private val appCache: AppCache,
) : JourneyCalendarRewardHelper {

    override suspend fun onRewarded() {
        appCache.putLong(PREF_KEY_REWARD_MILLIS, Instant.now().toEpochMilli())
    }

    override suspend fun hasAccessToCalendar(): Boolean {
        val savedMillis = appCache.getLong(PREF_KEY_REWARD_MILLIS, -1)
        if (savedMillis == -1L) return false
        return isSameCalendarDay(savedMillis)
    }

    private fun isSameCalendarDay(
        savedMillis: Long,
    ): Boolean {
        val savedDate = Instant
            .ofEpochMilli(savedMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val today = LocalDate.now(ZoneId.systemDefault())

        return savedDate == today
    }

    private companion object {
        const val PREF_KEY_REWARD_MILLIS: String = "PREF_KEY_REWARD_MILLIS"
    }
}
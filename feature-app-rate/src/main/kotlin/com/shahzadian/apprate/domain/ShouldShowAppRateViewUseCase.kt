package com.shahzadian.apprate.domain

import com.freenic.ui.appinfo.appdatacache.AppDataCache
import javax.inject.Inject

interface ShouldShowAppRateViewUseCase {

    suspend operator fun invoke(): Boolean
}

internal class ShouldShowAppRateViewUseCaseImpl @Inject constructor(
    private val appDataCache: AppDataCache,
) : ShouldShowAppRateViewUseCase {

    override suspend fun invoke(): Boolean {
        if (appDataCache.hasAppRated()) return false
        if (!isEligibleForRegistrationPeriod()) return false
        if (!isEligibleForRateCancel()) return false
        if (!isEligibleForSessionsCount()) return false
        return appDataCache.getHasCreatedSavingsGoal() ||
                appDataCache.hasSharedAchievement()

    }

    private suspend fun isEligibleForRateCancel(): Boolean {
        val rateCancelMillis = appDataCache.getRateCancelMillis() ?: return true
        return getPassedDaysSince(rateCancelMillis) >= RATE_CANCEL_DAYS_PERIOD
    }

    private suspend fun isEligibleForRegistrationPeriod(): Boolean {
        val registeredTimeMillis = appDataCache.getRegisteredTimeMillis() ?: return false
        return getPassedDaysSince(registeredTimeMillis) >= DAYS_SINCE_REGISTRATION_PERIOD
    }

    private suspend fun isEligibleForSessionsCount(): Boolean {
        val sessionsCount = appDataCache.getSessionsCount()
        return sessionsCount >= SESSIONS_COUNT_THRESHOLD
    }

    private fun getPassedDaysSince(millis: Long): Int {
        val delta = System.currentTimeMillis() - millis
        return (delta / (1000 * 60 * 60 * 24)).toInt()
    }

    private companion object {
        const val DAYS_SINCE_REGISTRATION_PERIOD: Long = 3
        const val RATE_CANCEL_DAYS_PERIOD: Long = 7
        const val SESSIONS_COUNT_THRESHOLD: Long = 5
    }
}

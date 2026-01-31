package com.shahzadian.ad.reward

import com.freenic.cache.AppCache
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

interface RewardHelperFactory {

    fun create(params: RewardHelper.Params): RewardHelper
}

internal class RewardHelperFactoryImpl @Inject constructor(
    private val appCache: AppCache,
) : RewardHelperFactory {

    override fun create(params: RewardHelper.Params): RewardHelper {
        return RewardHelperImpl(
            params = params,
            appCache = appCache,
        )
    }
}

interface RewardHelper {

    suspend fun onRewarded()

    suspend fun isRewardActual(): Boolean

    class Params(
        val featureKey: String,
        val duration: RewardDuration,
    )

    sealed interface RewardDuration {
        object Day : RewardDuration
    }
}

private class RewardHelperImpl(
    private val params: RewardHelper.Params,
    private val appCache: AppCache,
) : RewardHelper {

    private val cacheKey = "${params.featureKey}${PREF_KEY_REWARD_MILLIS}"

    override suspend fun onRewarded() {
        appCache.putLong(cacheKey, System.currentTimeMillis())
    }

    override suspend fun isRewardActual(): Boolean {
        val savedMillis = appCache.getLong(cacheKey, -1)
        if (savedMillis == -1L) return false
        return isRewardActual(savedMillis)
    }

    private fun isRewardActual(millis: Long): Boolean {
        return when (params.duration) {
            is RewardHelper.RewardDuration.Day -> {
                val savedDate = Instant
                    .ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

                val today = LocalDate.now(ZoneId.systemDefault())
                savedDate == today
            }
        }
    }

    private companion object {
        const val PREF_KEY_REWARD_MILLIS = "_reward_millis"
    }
}

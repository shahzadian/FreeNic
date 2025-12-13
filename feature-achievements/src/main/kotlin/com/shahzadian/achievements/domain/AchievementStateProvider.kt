package com.shahzadian.achievements.domain

import com.shahzadian.user.domain.UserDataHelper

interface AchievementStateProvider {

    suspend fun getAchievementState(achievement: Achievement): AchievementState
}

internal class AchievementStateProviderImpl(
    private val userDataHelper: UserDataHelper,
) : AchievementStateProvider {

    override suspend fun getAchievementState(achievement: Achievement): AchievementState {
        return resolveAchievementState(achievement = achievement)
    }

    private fun resolveAchievementState(achievement: Achievement): AchievementState {
        when (val item = achievement.achievementItem) {
            is AchievementItem.Cigs -> {
                val skippedCigs = userDataHelper.getSkippedCigsCount()
                if (skippedCigs >= item.necessaryCount) {
                    return AchievementState.Unlocked
                } else {
                    val percents = skippedCigs.toFloat() / item.necessaryCount.toFloat() * 100f
                    return AchievementState.Locked(
                        percentsReached = percents
                    )
                }
            }

            is AchievementItem.Days -> {
                val skippedDays = userDataHelper.getSkippedDaysCount()
                if (skippedDays >= item.necessaryCount) {
                    return AchievementState.Unlocked
                } else {
                    val percents = skippedDays.toFloat() / item.necessaryCount.toFloat() * 100f
                    return AchievementState.Locked(
                        percentsReached = percents
                    )
                }
            }
        }
    }
}

package com.shahzadian.goalsscreen.ui

import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel
import javax.inject.Inject

internal interface AchievementsSorter {

    fun sort(
        achievements: List<AchievementUiModel>,
        maxCountOnScreen: Int?,
    ): List<AchievementUiModel>
}

internal class AchievementsSorterImpl @Inject constructor(
) : AchievementsSorter {

    override fun sort(
        achievements: List<AchievementUiModel>,
        maxCountOnScreen: Int?,
    ): List<AchievementUiModel> {
        val sortedByState = sortByUnlockedFirst(achievements)
        return maxCountOnScreen
            ?.let { sortByCount(sortedByState, maxCountOnScreen) }
            ?: sortedByState
    }

    private fun sortByUnlockedFirst(achievements: List<AchievementUiModel>): List<AchievementUiModel> {
        return achievements.sortedByDescending {
            it.state.progressPercents
        }
    }

    private fun sortByCount(
        achievements: List<AchievementUiModel>,
        maxCountOnScreen: Int,
    ): List<AchievementUiModel> {
        if (achievements.size <= maxCountOnScreen) return achievements
        val firstLockedAchievementIndex =
            achievements.indexOfFirst { it.state is AchievementUiModel.AchievementStateUiModel.Locked }
        if (firstLockedAchievementIndex == -1) {
            return achievements.takeLast(maxCountOnScreen)
        }
        if (firstLockedAchievementIndex <= 1) {
            return achievements.take(maxCountOnScreen)
        }
        val sublist = achievements.subList(firstLockedAchievementIndex - 2, achievements.size)
        return sublist.take(maxCountOnScreen)
    }
}

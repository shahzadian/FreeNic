package com.shahzadian.goalsscreen.ui

import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewState
import javax.inject.Inject

internal interface AchievementsSorter {

    fun sort(achievements: List<GoalsViewState.AchievementUiModel>): List<GoalsViewState.AchievementUiModel>
}

internal class AchievementsSorterImpl @Inject constructor(
) : AchievementsSorter {

    override fun sort(achievements: List<GoalsViewState.AchievementUiModel>): List<GoalsViewState.AchievementUiModel> {
        val sortedByState = sortByUnlockedFirst(achievements)
        return sortByCount(sortedByState)
    }

    private fun sortByUnlockedFirst(achievements: List<GoalsViewState.AchievementUiModel>): List<GoalsViewState.AchievementUiModel> {
        return achievements.sortedBy {
            if (it.state is GoalsViewState.AchievementUiModel.AchievementStateUiModel.Unlocked) 0 else 1
        }
    }

    private fun sortByCount(
        achievements: List<GoalsViewState.AchievementUiModel>
    ): List<GoalsViewState.AchievementUiModel> {
        if (achievements.size <= AchievementsConst.MAX_ACHIEVEMENTS_ON_SCREEN) return achievements
        val firstLockedAchievementIndex =
            achievements.indexOfFirst { it.state is GoalsViewState.AchievementUiModel.AchievementStateUiModel.Locked }
        if (firstLockedAchievementIndex == -1) {
            return achievements.takeLast(AchievementsConst.MAX_ACHIEVEMENTS_ON_SCREEN)
        }
        if (firstLockedAchievementIndex <= 1) {
            return achievements.take(AchievementsConst.MAX_ACHIEVEMENTS_ON_SCREEN)
        }
        val sublist = achievements.subList(firstLockedAchievementIndex - 2, achievements.size)
        return sublist.take(AchievementsConst.MAX_ACHIEVEMENTS_ON_SCREEN)
    }
}

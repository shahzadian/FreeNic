package com.shahzadian.goalsscreen.ui

import com.shahzadian.achievements.domain.Achievement
import com.shahzadian.achievements.domain.AchievementState
import com.shahzadian.achievements.domain.AchievementStateProviderFactory
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel
import javax.inject.Inject

internal interface AchievementMapper {

    suspend fun map(achievement: Achievement): AchievementUiModel?
}

internal class AchievementMapperImpl @Inject constructor(
    private val achievementStateProviderFactory: AchievementStateProviderFactory,
) : AchievementMapper {

    override suspend fun map(achievement: Achievement): AchievementUiModel? {
        val achievementStateProvider = achievementStateProviderFactory.create() ?: return null
        val state = achievementStateProvider.getAchievementState(achievement)
        return AchievementUiModel(
            title = achievement.name,
            description = achievement.description,
            icon = achievement.icon,
            state = when (state) {
                is AchievementState.Unlocked -> AchievementUiModel.AchievementStateUiModel.Unlocked
                is AchievementState.Locked -> AchievementUiModel.AchievementStateUiModel.Locked(
                    progressPercents = state.percentsReached
                )
            }
        )
    }
}

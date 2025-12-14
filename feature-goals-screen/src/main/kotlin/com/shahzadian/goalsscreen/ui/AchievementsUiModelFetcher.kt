package com.shahzadian.goalsscreen.ui

import android.util.Log
import com.shahzadian.achievements.domain.GetAchievementsUseCase
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel
import javax.inject.Inject

internal interface AchievementsUiModelFetcher {

    suspend fun fetch(
        config: Config = Config(),
    ): AchievementsUiModelFetchResult

    class Config(
        val maxCountsOnScreen: Int? = AchievementsConst.MAX_ACHIEVEMENTS_ON_SCREEN
    )

    class AchievementsUiModelFetchResult(
        val achievements: List<AchievementUiModel>,
        val initialAchievementsCount: Int,
    )
}

internal class AchievementsUiModelFetcherImpl @Inject constructor(
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val achievementMapper: AchievementMapper,
    private val achievementsSorter: AchievementsSorter,
) : AchievementsUiModelFetcher {

    override suspend fun fetch(
        config: AchievementsUiModelFetcher.Config,
    ): AchievementsUiModelFetcher.AchievementsUiModelFetchResult {
        val initialAchievements = getAchievementsUseCase()
        val achievements = initialAchievements
            .mapNotNull { achievementMapper.map(it) }
        Log.w("LogMog", "ach size: ${achievements.size}")
        return AchievementsUiModelFetcher.AchievementsUiModelFetchResult(
            achievements = achievementsSorter.sort(
                achievements = achievements,
                maxCountOnScreen = config.maxCountsOnScreen
            ),
            initialAchievementsCount = initialAchievements.size
        )
    }
}

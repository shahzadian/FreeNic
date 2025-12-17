package com.shahzadian.goalsscreen.ui

import com.shahzadian.achievements.domain.GetAchievementsUseCase
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel
import javax.inject.Inject

internal interface AchievementsUiModelFetcher {

    suspend fun fetch(
        config: Config = Config(),
    ): AchievementsUiModelFetchResult

    suspend fun getAchievementById(id: String): AchievementUiModel?

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

    private var initialAchievementsCount: Int? = null
    private var cachedAchievements: List<AchievementUiModel>? = null

    override suspend fun fetch(
        config: AchievementsUiModelFetcher.Config,
    ): AchievementsUiModelFetcher.AchievementsUiModelFetchResult {
        if (cachedAchievements != null && initialAchievementsCount == config.maxCountsOnScreen) return AchievementsUiModelFetcher.AchievementsUiModelFetchResult(
            achievements = cachedAchievements!!,
            initialAchievementsCount = initialAchievementsCount!!
        )
        val initialAchievements = getAchievementsUseCase()
        val achievements = initialAchievements
            .mapNotNull { achievementMapper.map(it) }
        initialAchievementsCount = initialAchievements.size
        val sortedAchievements = achievementsSorter.sort(achievements, config.maxCountsOnScreen)
        cachedAchievements = sortedAchievements
        return AchievementsUiModelFetcher.AchievementsUiModelFetchResult(
            achievements = sortedAchievements,
            initialAchievementsCount = initialAchievements.size
        )
    }

    override suspend fun getAchievementById(id: String): AchievementUiModel? {
        cachedAchievements?.firstOrNull { it.id == id }?.let { return it }
        val achievement = getAchievementsUseCase().firstOrNull { it.id == id } ?: return null
        return achievementMapper.map(achievement)
    }
}

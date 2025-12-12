package com.shahzadian.achievements.domain

import com.shahzadian.achievements.data.AchievementRepository
import javax.inject.Inject

interface GetAchievementsUseCase {

    suspend operator fun invoke(): List<Achievement>
}

internal class GetAchievementsUseCaseImpl @Inject constructor(
    private val achievementRepository: AchievementRepository,
) : GetAchievementsUseCase {

    override suspend fun invoke(): List<Achievement> {
        return achievementRepository.getAchievements()
    }
}

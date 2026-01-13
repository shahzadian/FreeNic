package com.shahzadian.achievements.data

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.achievements.domain.Achievement
import com.shahzadian.achievements.domain.AchievementItem
import com.shahzadian.baseui.R
import javax.inject.Inject

internal interface AchievementRepository {

    suspend fun getAchievements(): List<Achievement>
}

internal class AchievementRepositoryImpl @Inject constructor() : AchievementRepository {

    override suspend fun getAchievements(): List<Achievement> {
        return getCigsCountBasedAchievements() + getDayBasedAchievements()
    }

    private fun getCigsCountBasedAchievements(): List<Achievement> {
        return listOf(
            Achievement(
                id = "First step",
                name = TextWrapper.FromRes(R.string.achievement_title_first_step),
                description = TextWrapper.FromRes(R.string.achievement_desc_first_step),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_first_step),
                achievementItem = AchievementItem.Days(1),
            ),
            Achievement(
                id = "First skipped pack",
                name = TextWrapper.FromRes(R.string.achievement_title_first_skipped_pack),
                description = TextWrapper.FromRes(R.string.achievement_desc_first_skipped_pack),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_first_avoided_pack),
                achievementItem = AchievementItem.Cigs(20),
            ),
            Achievement(
                id = "Fifty victories",
                name = TextWrapper.FromRes(R.string.achievement_title_fifty_victories),
                description = TextWrapper.FromRes(R.string.achievement_desc_fifty_victories),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_fifty_victories),
                achievementItem = AchievementItem.Cigs(50),
            ),
        )
    }

    private fun getDayBasedAchievements(): List<Achievement> {
        return listOf(
            Achievement(
                id = "Stability",
                name = TextWrapper.FromRes(R.string.achievement_title_stability),
                description = TextWrapper.FromRes(R.string.achievement_desc_stability),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_stability),
                achievementItem = AchievementItem.Days(3),
            ),
            Achievement(
                id = "Week of freedom",
                name = TextWrapper.FromRes(R.string.achievement_title_week_of_freedom),
                description = TextWrapper.FromRes(R.string.achievement_desc_week_of_freedom),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_week_of_freedom),
                achievementItem = AchievementItem.Days(7),
            ),
            Achievement(
                id = "Two weeks of strength",
                name = TextWrapper.FromRes(R.string.achievement_title_two_weeks_of_strength),
                description = TextWrapper.FromRes(R.string.achievement_desc_two_weeks_of_strength),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_two_weeks_of_strength),
                achievementItem = AchievementItem.Days(14),
            ),
            Achievement(
                id = "Habit forming",
                name = TextWrapper.FromRes(R.string.achievement_title_habit_forming),
                description = TextWrapper.FromRes(R.string.achievement_desc_habit_forming),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_habit_forming),
                achievementItem = AchievementItem.Days(21),
            ),
            Achievement(
                id = "Month of victory",
                name = TextWrapper.FromRes(R.string.achievement_title_month_of_victory),
                description = TextWrapper.FromRes(R.string.achievement_desc_month_of_victory),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_month_of_victory),
                achievementItem = AchievementItem.Days(30),
            ),
            Achievement(
                id = "3 months",
                name = TextWrapper.FromRes(R.string.achievement_title_3_months),
                description = TextWrapper.FromRes(R.string.achievement_desc_3_months),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_3_months),
                achievementItem = AchievementItem.Days(90),
            )
        )
    }
}

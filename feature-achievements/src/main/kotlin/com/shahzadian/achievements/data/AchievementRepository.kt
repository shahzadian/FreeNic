package com.shahzadian.achievements.data

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.achievements.domain.Achievement
import com.shahzadian.achievements.domain.AchievementItem
import com.shahzadian.baseui.R
import java.util.Currency
import javax.inject.Inject

internal interface AchievementRepository {

    suspend fun getAchievements(): List<Achievement>
}

internal class AchievementRepositoryImpl @Inject constructor() : AchievementRepository {

    override suspend fun getAchievements(): List<Achievement> {
        return getCigsCountBasedAchievements() + getDayBasedAchievements() + getMoneyBasedAchievements()
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
            Achievement(
                id = "100 cigs",
                name = TextWrapper.FromRes(R.string.achievement_title_100_cigs),
                description = TextWrapper.FromRes(R.string.achievement_desc_100_cigs),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_cigs),
                achievementItem = AchievementItem.Cigs(100)
            ),
            Achievement(
                id = "200 cigs",
                name = TextWrapper.FromRes(R.string.achievement_title_200_cigs),
                description = TextWrapper.FromRes(R.string.achievement_desc_200_cigs),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_cigs),
                achievementItem = AchievementItem.Cigs(200)
            ),
            Achievement(
                id = "500 cigs",
                name = TextWrapper.FromRes(R.string.achievement_title_500_cigs),
                description = TextWrapper.FromRes(R.string.achievement_desc_500_cigs),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_cigs),
                achievementItem = AchievementItem.Cigs(500)
            ),
            Achievement(
                id = "1000 cigs",
                name = TextWrapper.FromRes(R.string.achievement_title_1000_cigs),
                description = TextWrapper.FromRes(R.string.achievement_desc_1000_cigs),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_cigs),
                achievementItem = AchievementItem.Cigs(1000)
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
            ),
            Achievement(
                id = "6 months",
                name = TextWrapper.FromRes(R.string.achievement_title_6_months),
                description = TextWrapper.FromRes(R.string.achievement_desc_6_months),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_6_months),
                achievementItem = AchievementItem.Days(180),
            ),
            Achievement(
                id = "one year",
                name = TextWrapper.FromRes(R.string.achievement_title_12_months),
                description = TextWrapper.FromRes(R.string.achievement_desc_12_months),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_12_months),
                achievementItem = AchievementItem.Days(365),
            )
        )
    }

    private fun getMoneyBasedAchievements(): List<Achievement> {
        return listOf(
            Achievement(
                id = "10 bucks",
                name = TextWrapper.FromRes(R.string.achievement_title_10_bucks),
                description = TextWrapper.FromRes(R.string.achievement_desc_10_bucks),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_bucks),
                achievementItem = AchievementItem.Money(
                    necessaryAmount = 10f,
                    currency = Currency.getInstance("USD"),
                )
            ),
            Achievement(
                id = "20 bucks",
                name = TextWrapper.FromRes(R.string.achievement_title_20_bucks),
                description = TextWrapper.FromRes(R.string.achievement_desc_20_bucks),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_bucks),
                achievementItem = AchievementItem.Money(
                    necessaryAmount = 20f,
                    currency = Currency.getInstance("USD"),
                )
            ),
            Achievement(
                id = "30 bucks",
                name = TextWrapper.FromRes(R.string.achievement_title_30_bucks),
                description = TextWrapper.FromRes(R.string.achievement_desc_30_bucks),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_bucks),
                achievementItem = AchievementItem.Money(
                    necessaryAmount = 30f,
                    currency = Currency.getInstance("USD"),
                )
            ),
            Achievement(
                id = "40 bucks",
                name = TextWrapper.FromRes(R.string.achievement_title_40_bucks),
                description = TextWrapper.FromRes(R.string.achievement_desc_40_bucks),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_bucks),
                achievementItem = AchievementItem.Money(
                    necessaryAmount = 40f,
                    currency = Currency.getInstance("USD"),
                )
            ),
            Achievement(
                id = "50 bucks",
                name = TextWrapper.FromRes(R.string.achievement_title_50_bucks),
                description = TextWrapper.FromRes(R.string.achievement_desc_50_bucks),
                icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_bucks),
                achievementItem = AchievementItem.Money(
                    necessaryAmount = 50f,
                    currency = Currency.getInstance("USD"),
                )
            ),
        )
    }
}

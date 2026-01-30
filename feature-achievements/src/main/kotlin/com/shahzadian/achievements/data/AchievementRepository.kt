package com.shahzadian.achievements.data

import com.freenic.ui.model.CoroutineContexts
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.achievements.domain.Achievement
import com.shahzadian.achievements.domain.AchievementItem
import com.shahzadian.baseui.R
import kotlinx.coroutines.withContext
import java.util.Currency
import javax.inject.Inject

internal interface AchievementRepository {

    suspend fun getAchievements(): List<Achievement>
}

internal class AchievementRepositoryImpl @Inject constructor(
    private val coroutineContexts: CoroutineContexts,
) : AchievementRepository {

    private var achievements: List<Achievement>? = null

    override suspend fun getAchievements(): List<Achievement> {
        if (achievements != null) return achievements!!
        return withContext(coroutineContexts.default) {
            val toReturn =
                getCigsCountBasedAchievements() + getDayBasedAchievements() + getMoneyBasedAchievements()
            achievements = toReturn
            toReturn
        }
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
        ) + getOtherCigsBasedAchievements()
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

    private fun getOtherCigsBasedAchievements(): List<Achievement> {
        val toReturn = mutableListOf<Achievement>()
        var count = 100
        while (count < 10000) {
            toReturn.add(getCigsBasedAchievement(count))
            count += 50
        }
        return toReturn
    }

    private fun getMoneyBasedAchievements(): List<Achievement> {
        val initialList = listOf(
            getMoneyBasedAchievement(10f),
            getMoneyBasedAchievement(20f),
            getMoneyBasedAchievement(30f),
            getMoneyBasedAchievement(40f),
            getMoneyBasedAchievement(50f),
        )
        var bucks = 100f
        val toReturn = mutableListOf<Achievement>()
        toReturn.addAll(initialList)
        while (bucks <= 2000f) {
            toReturn.add(getMoneyBasedAchievement(bucks))
            bucks += 50f
        }
        return toReturn
    }

    private fun getCigsBasedAchievement(cigs: Int): Achievement {
        val title = TextWrapper.FromPlural(
            R.plurals.achievement_title_cigs,
            cigs,
            listOf(cigs)
        )
        val desc = TextWrapper.FromPlural(
            R.plurals.achievement_desc_cigs,
            cigs,
            listOf(cigs)
        )
        return Achievement(
            id = "$cigs cigs",
            name = title,
            description = desc,
            icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_cigs),
            achievementItem = AchievementItem.Cigs(cigs)
        )
    }

    private fun getMoneyBasedAchievement(count: Float): Achievement {
        return Achievement(
            id = "$count bucks",
            name = getBucksTitle(count),
            description = getBucksDescription(count),
            icon = ImageWrapper.FromRes(com.shahzadian.achievements.R.drawable.ic_achievement_bucks),
            achievementItem = AchievementItem.Money(
                necessaryAmount = count,
                currency = Currency.getInstance("USD"),
            )
        )
    }

    private fun getBucksTitle(count: Float): TextWrapper {
        return TextWrapper.FromString("$count$")
    }

    private fun getBucksDescription(count: Float): TextWrapper {
        return TextWrapper.FromRes(
            R.string.achievement_desc_bucks,
            listOf("$count$"),
        )
    }
}

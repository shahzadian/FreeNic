package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.achievements.domain.Achievement
import com.shahzadian.achievements.domain.AchievementItem
import com.shahzadian.achievements.domain.GetAchievementsUseCase
import com.shahzadian.baseui.R
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import java.time.Instant
import java.time.LocalDate

internal class AchievementEventsFinder(
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
) : JourneyCalendarEventsFinder() {

    private var _achievements: List<Achievement>? = null
    private var _userData: UserData? = null

    override suspend fun getEventsForDate(date: LocalDate): List<DayEventUiModel> {
        val userData = getUserData() ?: return emptyList()
        val achievements = getAchievements()
        return getDayBasedAchievementEvents(
            date = date,
            achievements = achievements,
            userData = userData
        ) + getCigsBasedAchievementEvents(
            date = date,
            achievements = achievements,
            userData = userData,
        )
    }

    private fun getCigsBasedAchievementEvents(
        date: LocalDate,
        achievements: List<Achievement>,
        userData: UserData,
    ): List<DayEventUiModel> {
        return achievements.mapNotNull { achievement ->
            val necessaryCigs =
                (achievement.achievementItem as? AchievementItem.Cigs)?.necessaryCount
                    ?: return@mapNotNull null
            val quitDate = userData.getQuitDate()
            val cigsPerDay = userData.cigarettesData.cigarettesPerDay
            val necessaryDaysToAchieve = necessaryCigs.toFloat() / cigsPerDay.toFloat()
            val achievementDay = quitDate.plusDays(necessaryDaysToAchieve.toLong())
            if (achievementDay != date) return@mapNotNull null
            DayEventUiModel(
                title = TextWrapper.FromRes(R.string.journey_calendar_event_achievement_title),
                subtitle = TextWrapper.FromPlural(
                    R.plurals.journey_calendar_event_no_cigs_based_achievement_subtitle,
                    quantity = necessaryCigs,
                    args = arrayListOf(necessaryCigs)
                ),
                icon = ImageWrapper.FromRes(com.shahzadian.journeycalendarscreen.R.drawable.ic_journey_calendar_event_achievement)
            )
        }
    }

    private fun getDayBasedAchievementEvents(
        date: LocalDate,
        achievements: List<Achievement>,
        userData: UserData,
    ): List<DayEventUiModel> {
        val quitDate = userData.getQuitDate()
        return achievements.mapNotNull { achievement ->
            val necessaryDays =
                (achievement.achievementItem as? AchievementItem.Days)?.necessaryCount
                    ?: return@mapNotNull null
            val achievementDay = quitDate.plusDays(necessaryDays.toLong())
            if (achievementDay != date) return@mapNotNull null
            DayEventUiModel(
                title = TextWrapper.FromRes(R.string.journey_calendar_event_achievement_title),
                subtitle = TextWrapper.FromPlural(
                    R.plurals.journey_calendar_event_day_based_achievement_subtitle,
                    quantity = necessaryDays,
                    args = arrayListOf(necessaryDays)
                ),
                icon = ImageWrapper.FromRes(com.shahzadian.journeycalendarscreen.R.drawable.ic_journey_calendar_event_achievement)
            )
        }
    }

    private suspend fun getAchievements(): List<Achievement> {
        return _achievements ?: let {
            val achievements = getAchievementsUseCase()
            _achievements = achievements
            achievements
        }
    }

    private suspend fun getUserData(): UserData? {
        return _userData ?: let {
            val userData = getUserDataUseCase()
            _userData = userData
            userData
        }
    }

    private fun UserData.getQuitDate(): LocalDate {
        val instant = Instant.ofEpochMilli(quitData.quitDateMillis)
        val zoneDateTime = instant.atZone(zone)
        return zoneDateTime.toLocalDate()
    }
}
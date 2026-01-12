package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.shahzadian.achievements.domain.Achievement
import com.shahzadian.achievements.domain.AchievementItem
import com.shahzadian.achievements.domain.GetAchievementsUseCase
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
        val quitDate = userData.getQuitDate()
        return achievements.firstOrNull { achievement ->
            val necessaryDays =
                (achievement.achievementItem as? AchievementItem.Days)?.necessaryCount
                    ?: return@firstOrNull false
            val achievementDay = quitDate.plusDays(necessaryDays.toLong())
            achievementDay == date
        }?.let {
            listOf(
                DayEventUiModel.Achievement
            )
        } ?: emptyList()
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
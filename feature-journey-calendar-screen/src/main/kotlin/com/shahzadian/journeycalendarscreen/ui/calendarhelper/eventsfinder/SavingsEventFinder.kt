package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import com.shahzadian.savingsgoal.domain.GetCurrentSavingsGoalUseCase
import com.shahzadian.savingsgoal.domain.SavingsGoal
import java.time.LocalDate

internal class SavingsEventFinder(
    private val getCurrentSavingsGoalUseCase: GetCurrentSavingsGoalUseCase,
) : JourneyCalendarEventsFinder() {

    private var _currentSavingsGoal: SavingsGoal? = null

    override suspend fun getEventsForDate(date: LocalDate): List<DayEventUiModel> {
        val goal = getCurrentSavingsGoal() ?: return emptyList()
        val today = LocalDate.now()
        if (today == date && goal.daysLeft == 0) {
            return listOf(
                DayEventUiModel.SavingsGoal
            )
        }
        val achieveDay = today.plusDays(goal.daysLeft.toLong())
        if (achieveDay == date) {
            return listOf(
                DayEventUiModel.SavingsGoal
            )
        }
        return emptyList()
    }

    private suspend fun getCurrentSavingsGoal(): SavingsGoal? {
        return _currentSavingsGoal ?: let {
            val goal = getCurrentSavingsGoalUseCase()
            _currentSavingsGoal = goal
            goal
        }
    }
}
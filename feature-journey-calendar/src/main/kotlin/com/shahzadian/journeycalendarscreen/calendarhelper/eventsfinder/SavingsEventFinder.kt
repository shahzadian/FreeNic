package com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayEvent
import com.shahzadian.savingsgoal.domain.GetCurrentSavingsGoalUseCase
import com.shahzadian.savingsgoal.domain.SavingsGoal
import java.time.LocalDate
import java.util.Currency

internal class SavingsEventFinder(
    private val getCurrentSavingsGoalUseCase: GetCurrentSavingsGoalUseCase,
) : JourneyCalendarEventsFinder() {

    private var _currentSavingsGoal: SavingsGoal? = null

    override suspend fun getEventsForDate(date: LocalDate): List<JourneyCalendarDayEvent> {
        val goal = getCurrentSavingsGoal() ?: return emptyList()
        val today = LocalDate.now()
        if (today == date && goal.daysLeft == 0) {
            return listOf(composeEvent(goal))
        }
        val achieveDay = today.plusDays(goal.daysLeft.toLong())
        if (achieveDay == date) {
            return listOf(composeEvent(goal))
        }
        return emptyList()
    }

    private fun composeEvent(
        savingsGoal: SavingsGoal,
    ): JourneyCalendarDayEvent {
        val displayableCurrency = try {
            savingsGoal.currency
                ?.let { Currency.getInstance(it).symbol } ?: ""
        } catch (t: Throwable) {
            ""
        }
        val displayableAmount = "${savingsGoal.necessaryAmount}$displayableCurrency"
        return JourneyCalendarDayEvent(
            title = TextWrapper.FromRes(R.string.journey_calendar_event_savings_goal_title),
            subtitle = TextWrapper.FromRes(
                R.string.journey_calendar_event_savings_goal_subtitle,
                arrayListOf(displayableAmount, savingsGoal.name)
            ),
            icon = ImageWrapper.FromRes(com.shahzadian.journeycalendar.R.drawable.ic_journey_calendar_legend_savings)
        )
    }

    private suspend fun getCurrentSavingsGoal(): SavingsGoal? {
        return _currentSavingsGoal ?: let {
            val goal = getCurrentSavingsGoalUseCase()
            _currentSavingsGoal = goal
            goal
        }
    }
}
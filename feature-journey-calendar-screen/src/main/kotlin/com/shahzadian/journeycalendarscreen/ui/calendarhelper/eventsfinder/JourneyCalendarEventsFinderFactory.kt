package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.shahzadian.achievements.domain.GetAchievementsUseCase
import com.shahzadian.savingsgoal.domain.GetCurrentSavingsGoalUseCase
import com.shahzadian.slip.slipcase.domain.GetSlipCasesUseCase
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import javax.inject.Inject

internal interface JourneyCalendarEventsFinderFactory {

    fun getAll(): List<JourneyCalendarEventsFinder>
}

internal class JourneyCalendarEventsFinderFactoryImpl @Inject constructor(
    private val getSlipCasesUseCase: GetSlipCasesUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val getCurrentSavingsGoalUseCase: GetCurrentSavingsGoalUseCase,
    private val userDataHelperFactory: UserDataHelperFactory,
) : JourneyCalendarEventsFinderFactory {

    override fun getAll(): List<JourneyCalendarEventsFinder> {
        return listOf(
            SlipEventsFinder(
                getSlipCasesUseCase = getSlipCasesUseCase,
            ),
            QuitStartEventFinder(
                getUserDataUseCase = getUserDataUseCase,
            ),
            AchievementEventsFinder(
                getAchievementsUseCase = getAchievementsUseCase,
                getUserDataUseCase = getUserDataUseCase,
                userDataHelperFactory = userDataHelperFactory,
            ),
            SavingsEventFinder(
                getCurrentSavingsGoalUseCase = getCurrentSavingsGoalUseCase,
            )
        )
    }
}

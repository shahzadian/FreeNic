package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import android.content.Context
import com.shahzadian.achievements.domain.GetAchievementsUseCase
import com.shahzadian.dailycheckin.domain.GetDailyCheckInForDayUseCase
import com.shahzadian.insightsscreen.domain.GetInsightChartsBlocksUseCase
import com.shahzadian.savingsgoal.domain.GetCurrentSavingsGoalUseCase
import com.shahzadian.slip.slipcase.domain.GetSlipCasesUseCase
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal interface JourneyCalendarEventsFinderFactory {

    fun getAll(): List<JourneyCalendarEventsFinder>
}

internal class JourneyCalendarEventsFinderFactoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val getSlipCasesUseCase: GetSlipCasesUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val getCurrentSavingsGoalUseCase: GetCurrentSavingsGoalUseCase,
    private val userDataHelperFactory: UserDataHelperFactory,
    private val getInsightChartsBlocksUseCase: GetInsightChartsBlocksUseCase,
    private val getDailyCheckInForDayUseCase: GetDailyCheckInForDayUseCase,
) : JourneyCalendarEventsFinderFactory {

    override fun getAll(): List<JourneyCalendarEventsFinder> {
        return listOf(
            NoCigEventsFinder(
                getUserDataUseCase = getUserDataUseCase,
                userDataHelperFactory = userDataHelperFactory,
            ),
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
            ),
            InsightChartsEventFinder(
                context = context,
                getInsightChartsBlocksUseCase = getInsightChartsBlocksUseCase,
            ),
            DailyCheckInEventFinder(
                getDailyCheckInForDayUseCase = getDailyCheckInForDayUseCase,
            )
        )
    }
}

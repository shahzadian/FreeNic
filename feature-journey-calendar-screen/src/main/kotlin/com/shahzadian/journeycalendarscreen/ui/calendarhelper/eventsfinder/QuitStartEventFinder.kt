package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import java.time.LocalDate

internal class QuitStartEventFinder(
    private val getUserDataUseCase: GetUserDataUseCase,
) : JourneyCalendarEventsFinder() {

    private var _userData: UserData? = null

    override suspend fun findEventsForDate(date: LocalDate): List<DayEventUiModel> {
        val userData = getUserData() ?: return emptyList()
        return if (userData.quitData.quitDateMillis in date.startOfDayMillis()..date.endOfDayMillis()) {
            listOf(
                DayEventUiModel.QuitStart
            )
        } else {
            emptyList()
        }
    }

    private suspend fun getUserData(): UserData? {
        return _userData ?: let {
            val userData = getUserDataUseCase()
            _userData = userData
            userData
        }
    }


}
package com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayEventUiModel
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import java.time.LocalDate

internal class QuitStartEventFinder(
    private val getUserDataUseCase: GetUserDataUseCase,
) : JourneyCalendarEventsFinder() {

    private var _userData: UserData? = null

    override suspend fun getEventsForDate(date: LocalDate): List<DayEventUiModel> {
        val userData = getUserData() ?: return emptyList()
        return if (userData.quitData.quitDateMillis in date.startOfDayMillis()..date.endOfDayMillis()) {
            listOf(
                DayEventUiModel(
                    title = TextWrapper.FromRes(R.string.journey_calendar_event_quit_start_title),
                    subtitle = TextWrapper.FromRes(R.string.journey_calendar_event_quit_start_subtitle),
                    icon = ImageWrapper.FromRes(com.shahzadian.journeycalendarscreen.R.drawable.ic_journey_calendar_event_quit_start)
                )
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
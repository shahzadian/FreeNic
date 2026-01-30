package com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayEvent
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Currency

internal class NoCigEventsFinder(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val userDataHelperFactory: UserDataHelperFactory,
) : JourneyCalendarEventsFinder() {

    private var _userData: UserData? = null
    private var _userDataHelper: UserDataHelper? = null

    override suspend fun getEventsForDate(date: LocalDate): List<JourneyCalendarDayEvent> {
        val dateMillis = date.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
            ?.let { it + 1000 * 60 * 60 * 24 } ?: return emptyList()
        val userData = getUserData() ?: return emptyList()
        val userDataHelper = getUserDataHelper() ?: return emptyList()
        val noCigDays = dateMillis.takeIf { date.isInSmokeQuitPeriod() }?.let { millis ->
            userDataHelper.calculateSkippedDaysForMillis(millis)
        } ?: return emptyList()
        val savedMoney = dateMillis.takeIf { date.isInSmokeQuitPeriod() }?.let { millis ->
            val userDataHelper = getUserDataHelper() ?: return@let null
            userDataHelper.calculateSavedMoneyForMillis(millis)
        } ?: return emptyList()
        val currency = userData.cigarettesData.priceData.currency.let {
            try {
                Currency.getInstance(it).symbol
            } catch (t: Throwable) {
                return emptyList()
            }
        }
        val displayableSavedMoney = "${savedMoney}$currency"
        return listOf(
            JourneyCalendarDayEvent(
                title = TextWrapper.FromRes(R.string.journey_calendar_event_no_cig_title),
                subtitle = TextWrapper.FromPlural(
                    R.plurals.journey_calendar_event_no_cig_subtitle,
                    noCigDays,
                    arrayListOf(noCigDays, displayableSavedMoney)
                ),
                icon = ImageWrapper.FromRes(com.shahzadian.journeycalendar.R.drawable.ic_journey_calendar_event_no_cig_days),
            )
        )
    }


    private suspend fun LocalDate.isInSmokeQuitPeriod(): Boolean {
        return this >= getQuitDate()
    }

    private suspend fun getQuitDate(): LocalDate? {
        val userData = getUserData() ?: return null
        val instant = Instant.ofEpochMilli(userData.quitData.quitDateMillis)
        val zone = ZoneId.systemDefault()
        return instant.atZone(zone).toLocalDate()
    }


    private suspend fun getUserData(): UserData? {
        return _userData ?: run {
            _userData = getUserDataUseCase.invoke()
            _userData
        }
    }


    private suspend fun getUserDataHelper(): UserDataHelper? {
        val userData = getUserData() ?: return null
        return _userDataHelper ?: let {
            val helper = userDataHelperFactory.create(userData)
            _userDataHelper = helper
            helper
        }
    }
}
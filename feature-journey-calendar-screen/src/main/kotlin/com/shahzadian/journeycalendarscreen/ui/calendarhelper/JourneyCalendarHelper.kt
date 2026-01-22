package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder.JourneyCalendarEventsFinderFactory
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

internal interface JourneyCalendarHelper {

    suspend fun hasPreviousMonth(): Boolean

    suspend fun getMonth(): MonthDataUiModel

    suspend fun decrementMonth()

    suspend fun incrementMonth()
}

internal class JourneyCalendarHelperImpl @Inject constructor(
    private val eventsFinderFactory: JourneyCalendarEventsFinderFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val userDataHelperFactory: UserDataHelperFactory,
) : JourneyCalendarHelper {

    private var currentMonth: YearMonth = YearMonth.now()

    private val eventsFinders by lazyUnsafe {
        eventsFinderFactory.getAll()
    }

    private var _userData: UserData? = null
    private var _userDataHelper: UserDataHelper? = null

    private val mutex by lazyUnsafe {
        Mutex()
    }

    override suspend fun hasPreviousMonth(): Boolean {
        mutex.withLock {
            val quitDate = getQuitDate() ?: return false
            if (quitDate.year < currentMonth.year) return true
            return quitDate.month < currentMonth.month
        }
    }

    override suspend fun getMonth(): MonthDataUiModel {
        mutex.withLock {
            val title = TextWrapper.FromString(
                currentMonth.month.getDisplayName(
                    TextStyle.FULL_STANDALONE, Locale.getDefault()
                ).plus(" ").plus(currentMonth.year)
            )

            val firstOfMonth = currentMonth.atDay(1)
            val daysInMonth = currentMonth.lengthOfMonth()
            val firstDayOfWeek = firstOfMonth.dayOfWeek

            // We use Monday as first day (1..7)
            val leadingBlanks =
                ((firstDayOfWeek.value + 6) % 7) // Monday=1 -> 0 blanks, Sunday=7 -> 6 blanks

            val totalCellsBase = leadingBlanks + daysInMonth
            val trailingBlanks = (7 - (totalCellsBase % 7)) % 7
            val totalCells = totalCellsBase + trailingBlanks

            val today = LocalDate.now()

            val cells = (0 until totalCells).map { index ->
                val dayNumber = index - leadingBlanks + 1
                val inCurrentMonth = dayNumber in 1..daysInMonth
                val date = if (inCurrentMonth) {
                    currentMonth.atDay(dayNumber)
                } else {
                    null
                }
                DayCellUiModel(
                    calendarDayText = if (inCurrentMonth) {
                        TextWrapper.FromString(dayNumber.toString())
                    } else {
                        null
                    },
                    fullDayText = date?.getFullDayText(),
                    isInCurrentMonth = inCurrentMonth,
                    isToday = date == today,
                    isInFuture = date?.isAfter(today) ?: false,
                    isInSmokeQuitPeriod = date?.isInSmokeQuitPeriod() ?: false,
                    noCigData = date?.toNoCigData(),
                    events = date?.let { localDate ->
                        eventsFinders.map { it.findEventsForDate(localDate) }.flatten()
                    } ?: emptyList(),
                    maxDisplayableEvents = MAX_DISPLAYABLE_EVENTS,
                )
            }

            val weeks = cells.chunked(7)
            return MonthDataUiModel(
                monthTitle = title,
                weeks = weeks,
            )
        }
    }

    override suspend fun decrementMonth() {
        mutex.withLock {
            currentMonth = currentMonth.minusMonths(1)
        }
    }

    override suspend fun incrementMonth() {
        mutex.withLock {
            currentMonth = currentMonth.plusMonths(1)
        }
    }

    private suspend fun LocalDate?.toNoCigData(): DayCellUiModel.NoCigData? {
        val dateMillis = this?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
            ?.let { it + 1000 * 60 * 60 * 24 } ?: return null
        val userData = getUserData() ?: return null
        val userDataHelper = getUserDataHelper() ?: return null
        val noCigDays = dateMillis.takeIf { this.isInSmokeQuitPeriod() }?.let { millis ->
            userDataHelper.calculateSkippedDaysForMillis(millis)
        } ?: return null
        val savedMoney = dateMillis.takeIf { this.isInSmokeQuitPeriod() }?.let { millis ->
            val userDataHelper = getUserDataHelper() ?: return@let null
            userDataHelper.calculateSavedMoneyForMillis(millis)
        } ?: return null
        val currency = userData.cigarettesData.priceData.currency.let {
            try {
                Currency.getInstance(it).symbol
            } catch (t: Throwable) {
                return null
            }
        }
        val displayableSavedMoney = "${savedMoney}$currency"
        return DayCellUiModel.NoCigData(
            noCigDays = noCigDays,
            displayableSavedMoney = displayableSavedMoney,
        )
    }

    private fun LocalDate.getFullDayText(): TextWrapper {
        val formatter =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault())

        return TextWrapper.FromString(formatter.format(this))
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

    private companion object {
        const val MAX_DISPLAYABLE_EVENTS: Int = 3
    }
}

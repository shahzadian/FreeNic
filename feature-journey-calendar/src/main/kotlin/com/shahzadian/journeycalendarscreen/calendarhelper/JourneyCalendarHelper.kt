package com.shahzadian.journeycalendarscreen.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder.JourneyCalendarEventsFinderFactory
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
import java.util.Locale
import javax.inject.Inject

interface JourneyCalendarHelper {

    val rewardHelper: JourneyCalendarRewardHelper

    suspend fun hasPreviousMonth(): Boolean

    suspend fun getMonth(): JourneyCalendarMonthData

    suspend fun decrementMonth()

    suspend fun incrementMonth()

    /**
     * Возвращает данные для сегодняшнего дня: локализованное название дня недели
     * и список событий, релевантных для текущей даты.
     */
    suspend fun getTodayData(): JourneyCalendarTodayData
}

internal class JourneyCalendarHelperImpl @Inject constructor(
    private val eventsFinderFactory: JourneyCalendarEventsFinderFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
    override val rewardHelper: JourneyCalendarRewardHelper,
) : JourneyCalendarHelper {

    private var currentMonth: YearMonth = YearMonth.now()

    private val eventsFinders by lazyUnsafe {
        eventsFinderFactory.getAll()
    }

    private var _userData: UserData? = null

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

    override suspend fun getMonth(): JourneyCalendarMonthData {
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
                JourneyCalendarDayCell(
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
                    events = date?.let { localDate ->
                        eventsFinders.map { it.findEventsForDate(localDate) }.flatten()
                    } ?: emptyList(),
                    maxDisplayableEvents = MAX_DISPLAYABLE_EVENTS,
                )
            }

            val weeks = cells.chunked(7)
            return JourneyCalendarMonthData(
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

    override suspend fun getTodayData(): JourneyCalendarTodayData {
        mutex.withLock {
            val today = LocalDate.now()
            val events = eventsFinders.map { it.findEventsForDate(today) }.flatten()
            return JourneyCalendarTodayData(
                todayDisplayableDate = today.getFullDayText(),
                events = events,
            )
        }
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

    private companion object {
        const val MAX_DISPLAYABLE_EVENTS: Int = 3
    }
}

@Immutable
data class JourneyCalendarTodayData(
    val todayDisplayableDate: TextWrapper,
    val events: List<JourneyCalendarDayEvent>,
)

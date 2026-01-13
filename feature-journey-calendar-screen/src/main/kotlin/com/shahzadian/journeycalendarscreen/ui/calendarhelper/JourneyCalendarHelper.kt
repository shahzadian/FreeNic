package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.eventsfinder.JourneyCalendarEventsFinderFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

internal interface JourneyCalendarHelper {

    suspend fun getMonth(): MonthDataUiModel

    suspend fun decrementMonth()

    suspend fun incrementMonth()
}

internal class JourneyCalendarHelperImpl @Inject constructor(
    private val eventsFinderFactory: JourneyCalendarEventsFinderFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
) : JourneyCalendarHelper {

    private var currentMonth: YearMonth = YearMonth.now()

    private val eventsFinders by lazyUnsafe {
        eventsFinderFactory.getAll()
    }

    private var _userData: UserData? = null

    override suspend fun getMonth(): MonthDataUiModel {
        val title = TextWrapper.FromString(
            currentMonth.month.getDisplayName(
                TextStyle.FULL_STANDALONE,
                Locale.getDefault()
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
                isInSmokeQuitPeriod = date?.isInSmokeQuitPeriod() ?: false,
                events = date?.let { localDate ->
                    eventsFinders
                        .map { it.findEventsForDate(localDate) }.flatten()
                } ?: emptyList(),
            )
        }

        val weeks = cells.chunked(7)
        return MonthDataUiModel(
            monthTitle = title,
            weeks = weeks,
        )
    }

    override suspend fun decrementMonth() {
        currentMonth = currentMonth.minusMonths(1)
    }

    override suspend fun incrementMonth() {
        currentMonth = currentMonth.plusMonths(1)
    }

    private fun LocalDate.getFullDayText(): TextWrapper {
        val formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(Locale.getDefault())

        return TextWrapper.FromString(formatter.format(this))
    }

    private suspend fun LocalDate.isInSmokeQuitPeriod(): Boolean {
        val userData = getUserData() ?: return false
        val instant = Instant.ofEpochMilli(userData.quitData.quitDateMillis)
        val zone = ZoneId.systemDefault()
        val quitDate = instant.atZone(zone).toLocalDate()
        return this >= quitDate
    }

    private suspend fun getUserData(): UserData? {
        return _userData ?: run {
            _userData = getUserDataUseCase.invoke()
            _userData
        }
    }
}

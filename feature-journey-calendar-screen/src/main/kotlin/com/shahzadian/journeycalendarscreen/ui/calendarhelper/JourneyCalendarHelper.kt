package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import com.freenic.ui.wrapper.TextWrapper
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

internal interface JourneyCalendarHelper {

    suspend fun getMonth(): MonthDataUiModel

    suspend fun decrementMonth()

    suspend fun incrementMonth()
}

internal class JourneyCalendarHelperImpl @Inject constructor() : JourneyCalendarHelper {

    private var currentMonth: YearMonth = YearMonth.now()

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
            if (dayNumber in 1..daysInMonth) {
                val date = currentMonth.atDay(dayNumber)
                DayCellUiModel(
                    date = date,
                    dayText = TextWrapper.FromString(dayNumber.toString()),
                    inCurrentMonth = true,
                    isToday = date == today,
                    isEnabled = true,
                )
            } else {
                DayCellUiModel(
                    date = null,
                    dayText = null,
                    inCurrentMonth = false,
                    isToday = false,
                    isEnabled = false,
                )
            }
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
}
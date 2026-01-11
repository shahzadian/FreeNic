package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.slip.slipcase.domain.GetSlipCasesUseCase
import com.shahzadian.slip.slipcase.domain.SlipCase
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

internal interface JourneyCalendarHelper {

    suspend fun getMonth(): MonthDataUiModel

    suspend fun decrementMonth()

    suspend fun incrementMonth()
}

internal class JourneyCalendarHelperImpl @Inject constructor(
    private val getSlipCasesUseCase: GetSlipCasesUseCase,
) : JourneyCalendarHelper {

    private var currentMonth: YearMonth = YearMonth.now()

    private var _slipCases: List<SlipCase>? = null

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
                dayText = if (inCurrentMonth) {
                    TextWrapper.FromString(dayNumber.toString())
                } else {
                    null
                },
                isInCurrentMonth = inCurrentMonth,
                isToday = date == today,
                events = date?.getEventsForDay() ?: emptyList(),
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

    private suspend fun getSlipCases(): List<SlipCase> {
        return _slipCases ?: let {
            val cases = getSlipCasesUseCase()
            _slipCases = cases
            cases
        }
    }

    private suspend fun LocalDate.getEventsForDay(): List<DayEventUiModel> {
        val zone = ZoneId.systemDefault()
        val startOfDay = this
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()
        val endOfDay = this
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()
        val slipCases = getSlipCases()
        val casesInDay = slipCases
            .mapNotNull { case ->
                if (case.slipDateMillis in startOfDay..endOfDay) {
                    DayEventUiModel.Slip
                } else {
                    null
                }
            }
        return casesInDay
    }
}
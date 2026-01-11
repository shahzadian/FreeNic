package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class JourneyCalendarViewModel @Inject constructor() :
    BaseViewModel<JourneyCalendarViewState, JourneyCalendarViewEvent, JourneyCalendarViewCommand>() {

    override val initState: JourneyCalendarViewState by lazyUnsafe {
        JourneyCalendarViewState.Loading
    }

    init {
        viewModelScope.launch {
            setMonth(YearMonth.now())
        }
    }

    override fun onViewEventHappened(event: JourneyCalendarViewEvent) {

    }

    private var currentMonth: YearMonth = YearMonth.now()
    private var selectedDate: LocalDate? = null

    private suspend fun setMonth(month: YearMonth) {
        currentMonth = month
        setViewState(buildDataState())
    }

    private fun buildDataState(): JourneyCalendarViewState.Data {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
        val title = TextWrapper.FromString(currentMonth.atDay(1).format(formatter))

        val firstOfMonth = currentMonth.atDay(1)
        val daysInMonth = currentMonth.lengthOfMonth()
        val firstDayOfWeek = firstOfMonth.dayOfWeek

        // We use Monday as first day (1..7)
        val leadingBlanks = ((firstDayOfWeek.value + 6) % 7) // Monday=1 -> 0 blanks, Sunday=7 -> 6 blanks

        val totalCellsBase = leadingBlanks + daysInMonth
        val trailingBlanks = (7 - (totalCellsBase % 7)) % 7
        val totalCells = totalCellsBase + trailingBlanks

        val today = LocalDate.now()

        val cells = (0 until totalCells).map { index ->
            val dayNumber = index - leadingBlanks + 1
            if (dayNumber in 1..daysInMonth) {
                val date = currentMonth.atDay(dayNumber)
                JourneyCalendarViewState.Data.DayCellUi(
                    date = date,
                    dayText = TextWrapper.FromString(dayNumber.toString()),
                    inCurrentMonth = true,
                    isToday = date == today,
                    isSelected = selectedDate == date,
                    isEnabled = true,
                    onClick = {
                        if (selectedDate != date) {
                            viewModelScope.launch {
                                selectedDate = date
                                setViewState(buildDataState())
                            }
                        }
                    }
                )
            } else {
                JourneyCalendarViewState.Data.DayCellUi(
                    date = null,
                    dayText = null,
                    inCurrentMonth = false,
                    isToday = false,
                    isSelected = false,
                    isEnabled = false,
                    onClick = null,
                )
            }
        }

        val weeks = cells.chunked(7)

        return JourneyCalendarViewState.Data(
            monthTitle = title,
            weeks = weeks,
            onPrevMonth = {
                viewModelScope.launch {
                    setMonth(currentMonth.minusMonths(1))
                }
            },
            onNextMonth = {
                viewModelScope.launch {
                    setMonth(currentMonth.plusMonths(1))
                }
            },
        )
    }
}
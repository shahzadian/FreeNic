package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class DayCellUiModel(
    val calendarDayText: TextWrapper?,
    val fullDayText: TextWrapper?,
    val isInCurrentMonth: Boolean,
    val isInSmokeQuitPeriod: Boolean,
    val isToday: Boolean,
    val isInFuture: Boolean,
    val noCigData: NoCigData?,
    val events: List<DayEventUiModel>,
    val maxDisplayableEvents: Int,
) {

    data class NoCigData(
        val noCigDays: Int,
        val displayableSavedMoney: String,
    )
}


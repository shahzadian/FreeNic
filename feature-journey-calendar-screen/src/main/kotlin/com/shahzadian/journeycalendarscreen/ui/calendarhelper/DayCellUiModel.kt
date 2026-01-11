package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.TextWrapper
import java.time.LocalDate

@Immutable
internal data class DayCellUiModel(
    val date: LocalDate?,
    val dayText: TextWrapper?,
    val inCurrentMonth: Boolean,
    val isToday: Boolean,
    val isEnabled: Boolean,
)
package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class MonthDataUiModel(
    val monthTitle: TextWrapper,
    val weeks: List<List<DayCellUiModel>>,
)

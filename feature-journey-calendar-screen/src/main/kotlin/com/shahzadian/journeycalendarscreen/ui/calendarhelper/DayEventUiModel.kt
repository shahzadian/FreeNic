package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class DayEventUiModel(
    val title: TextWrapper,
    val subtitle: TextWrapper?,
    val icon: ImageWrapper,
    val showTypes: List<DayEventShowType> = listOf(
        DayEventShowType.BottomSheet,
        DayEventShowType.Preview,
    )
)

internal sealed interface DayEventShowType {
    data object Preview : DayEventShowType
    data object BottomSheet : DayEventShowType
}
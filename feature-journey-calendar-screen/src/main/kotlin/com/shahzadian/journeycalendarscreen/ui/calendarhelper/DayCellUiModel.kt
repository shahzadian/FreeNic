package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.journeycalendarscreen.R

@Immutable
internal data class DayCellUiModel(
    val dayText: TextWrapper?,
    val isInCurrentMonth: Boolean,
    val isToday: Boolean,
    val events: List<DayEventUiModel>,
)

@Immutable
internal sealed interface DayEventUiModel {

    val icon: ImageWrapper

    data object Slip : DayEventUiModel {

        override val icon: ImageWrapper by lazyUnsafe {
            ImageWrapper.FromRes(R.drawable.ic_journey_calendar_event_slip)
        }
    }
}
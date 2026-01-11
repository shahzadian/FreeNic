package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper
import java.time.LocalDate

@Immutable
internal sealed interface JourneyCalendarViewState : ViewState {

    @Immutable
    data object Loading : JourneyCalendarViewState

    @Immutable
    data class Data(
        val monthTitle: TextWrapper,
        val weeks: List<List<DayCellUi>>,
        val onPrevMonth: () -> Unit,
        val onNextMonth: () -> Unit,
    ) : JourneyCalendarViewState {

        @Immutable
        data class DayCellUi(
            val date: LocalDate?,
            val dayText: TextWrapper?,
            val inCurrentMonth: Boolean,
            val isToday: Boolean,
            val isSelected: Boolean,
            val isEnabled: Boolean,
            val onClick: (() -> Unit)? = null,
        )
    }
}
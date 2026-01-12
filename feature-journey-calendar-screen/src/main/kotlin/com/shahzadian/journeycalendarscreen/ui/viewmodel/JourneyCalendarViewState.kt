package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.MonthDataUiModel

@Immutable
internal sealed interface JourneyCalendarViewState : ViewState {

    @Immutable
    data object Loading : JourneyCalendarViewState

    @Immutable
    data class Data(
        val monthDataUiModel: MonthDataUiModel,
        // changeId гарантирует, что при каждом setData будет новый экземпляр состояния,
        // даже если вложенные модели равны по equals -> заставляет Compose перерисовать контент
        val changeId: Long = System.nanoTime(),
    ) : JourneyCalendarViewState
}

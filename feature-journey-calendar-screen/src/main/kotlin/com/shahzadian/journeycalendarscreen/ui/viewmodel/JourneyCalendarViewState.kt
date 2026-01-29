package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayCellUiModel
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.MonthDataUiModel

@Immutable
internal sealed interface JourneyCalendarViewState : ViewState {

    @Immutable
    data object Loading : JourneyCalendarViewState

    @Immutable
    data class Preview(
        val previewRewardState: PreviewRewardState,
    ) : JourneyCalendarViewState

    @Immutable
    data class Data(
        val calendarAccessType: CalendarAccessType,
        val monthDataUiModel: MonthDataUiModel,
        val hasPreviousMonth: Boolean,
        val tappedDay: DayCellUiModel?,
        // changeId гарантирует, что при каждом setData будет новый экземпляр состояния,
        // даже если вложенные модели равны по equals -> заставляет Compose перерисовать контент
        val changeId: Long = System.nanoTime(),
    ) : JourneyCalendarViewState

    @Immutable
    sealed interface PreviewRewardState {

        data object Default : PreviewRewardState
        data object Loading : PreviewRewardState
    }

    @Immutable
    sealed interface CalendarAccessType {
        data object Permanent : CalendarAccessType
        data object UntilTheDayEnd : CalendarAccessType
        data object NoAccess : CalendarAccessType
    }
}

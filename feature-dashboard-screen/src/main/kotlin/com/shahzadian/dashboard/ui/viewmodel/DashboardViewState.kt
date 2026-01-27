package com.shahzadian.dashboard.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface DashboardViewState : ViewState {

    @Immutable
    data object Loading : DashboardViewState

    data class Data(
        val analyticsData: DashboardAnalyticsUiModel,
        val motivationalNote: MotivationalNoteUiModel,
        val dailyCheckIn: DailyCheckInUiModel,
    ) : DashboardViewState

    @Immutable
    data class DashboardAnalyticsUiModel(
        val noCigDays: Int,
        val savedMoney: Float,
        val currency: String,
    )

    @Immutable
    data class MotivationalNoteUiModel(
        val text: TextWrapper,
    )

    sealed interface DailyCheckInUiModel {
        data object Selection : DailyCheckInUiModel
        data object Confirmation : DailyCheckInUiModel
        data object Hide : DailyCheckInUiModel
    }

    enum class DailyCheckInResult {
        HARD,
        OKAY,
        HAPPY
    }
}

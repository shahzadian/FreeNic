package com.shahzadian.dashboard.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface DashboardViewState : ViewState {

    @Immutable
    data object Loading : DashboardViewState

    @Immutable
    data class Data(
        val analyticsData: DashboardAnalyticsUiModel,
        val healthInsightsUiModel: HealthInsightsUiModel,
        val motivationalNote: MotivationalNoteUiModel,
    ) : DashboardViewState

    @Immutable
    data class DashboardAnalyticsUiModel(
        val noCigDays: Int,
        val savedMoney: Float,
        val currency: String,
    )

    @Immutable
    data class HealthInsightsUiModel(
        val label: TextWrapper,
        val progress: Int,
    )

    @Immutable
    data class MotivationalNoteUiModel(
        val text: TextWrapper,
    )
}

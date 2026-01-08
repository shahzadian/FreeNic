package com.shahzadian.dashboard.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface DashboardViewState : ViewState {

    @Immutable
    data object Loading : DashboardViewState

    @Immutable
    data class Data(
        val mainProgressDataUiModel: MainProgressDataUiModel,
    ) : DashboardViewState

    @Immutable
    data class MainProgressDataUiModel(
        val noCigDays: Int,
        val savedMoney: Float,
        val currency: String?,
    )
}

package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface GoalsViewState : ViewState {

    @Immutable
    data object Loading : GoalsViewState

    @Immutable
    data class Data(
        val hasPaidSubscription: Boolean,
        val achievementsData: AchievementsData,
    ) : GoalsViewState {

    }

}

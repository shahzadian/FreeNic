package com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementsData

@Immutable
internal sealed interface AchievementsViewState : ViewState {

    @Immutable
    data object Loading : AchievementsViewState

    @Immutable
    data class Data(
        val achievementsData: AchievementsData,
    ) : AchievementsViewState
}

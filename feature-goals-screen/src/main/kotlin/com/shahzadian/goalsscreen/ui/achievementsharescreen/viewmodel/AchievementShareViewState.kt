package com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Immutable
internal sealed interface AchievementShareViewState : ViewState {

    data object Loading : AchievementShareViewState

    data class Data(
        val achievementToShare: AchievementUiModel,
    ) : AchievementShareViewState
}

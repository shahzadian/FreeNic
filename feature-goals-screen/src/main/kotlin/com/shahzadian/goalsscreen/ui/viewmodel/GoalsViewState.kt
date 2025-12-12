package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface GoalsViewState : ViewState {

    @Immutable
    data object Loading : GoalsViewState

    @Immutable
    data class Data(
        val achievements: List<AchievementUiModel>,
    ) : GoalsViewState

    @Immutable
    data class AchievementUiModel(
        val title: TextWrapper,
        val description: TextWrapper,
        val icon: ImageWrapper,
        val state: AchievementStateUiModel,
    ) {

        @Immutable
        sealed interface AchievementStateUiModel {

            @Immutable
            object Unlocked : AchievementStateUiModel

            @Immutable
            data class Locked(val percents: Float) : AchievementStateUiModel
        }
    }
}

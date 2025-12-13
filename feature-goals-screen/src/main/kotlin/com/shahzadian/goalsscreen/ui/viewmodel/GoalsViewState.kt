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
        val achievementsData: AchievementsData,
    ) : GoalsViewState {

        @Immutable
        data class AchievementsData(
            val achievements: List<AchievementUiModel>,
            val shouldShowMoreAchievementsButton: Boolean,
            val achievementsPerRow: Int,
        )
    }

    @Immutable
    data class AchievementUiModel(
        val title: TextWrapper,
        val description: TextWrapper,
        val icon: ImageWrapper,
        val state: AchievementStateUiModel,
    ) {

        @Immutable
        sealed interface AchievementStateUiModel {

            val progressPercents: Float

            @Immutable
            object Unlocked : AchievementStateUiModel {

                override val progressPercents: Float
                    get() = 100f
            }

            @Immutable
            data class Locked(
                override val progressPercents: Float
            ) : AchievementStateUiModel {
            }
        }
    }
}

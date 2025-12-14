package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class AchievementUiModel(
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
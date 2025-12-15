package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class AchievementUiModel(
    val id: String,
    val title: TextWrapper,
    val description: TextWrapper,
    val icon: ImageWrapper,
    val state: AchievementStateUiModel,
    val item: AchievementItemUiModel,
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

    @Immutable
    sealed interface AchievementItemUiModel {

        @Immutable
        data class Cigs(val count: Int) : AchievementItemUiModel

        @Immutable
        data class Days(val count: Int) : AchievementItemUiModel
    }
}

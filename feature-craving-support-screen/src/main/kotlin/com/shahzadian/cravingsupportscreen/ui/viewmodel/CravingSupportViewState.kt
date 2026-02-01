package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal sealed interface CravingSupportViewState : ViewState {

    data object Loading : CravingSupportViewState

    data class Data(
        val selectedCravingStrength: CravingStrength?,
        val activities: ImmutableList<CravingSupportActivityUiModel>,
        val otherActivities: ImmutableList<CravingSupportActivityUiModel>,
    ) : CravingSupportViewState

    data class CravingSupportActivityUiModel(
        val id: String,
        val name: TextWrapper,
        val description: TextWrapper,
        val icon: ImageWrapper,
    )

    sealed interface CravingStrength {
        val emoji: String
        val labelRes: Int

        data object Low : CravingStrength {
            override val emoji: String = "\uD83D\uDE0C"
            override val labelRes: Int =
                com.shahzadian.baseui.R.string.craving_support_craving_strength_low
        }

        data object Middle : CravingStrength {
            override val emoji: String = "\uD83D\uDE10"
            override val labelRes: Int =
                com.shahzadian.baseui.R.string.craving_support_craving_strength_mid
        }

        data object Strong : CravingStrength {
            override val emoji: String = "\uD83D\uDE23"
            override val labelRes: Int =
                com.shahzadian.baseui.R.string.craving_support_craving_strength_strong
        }
    }
}

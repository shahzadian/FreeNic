package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface CravingSupportViewState : ViewState {

    data object Loading : CravingSupportViewState

    data class Data(
        val motivationalNote: TextWrapper,
        val selectedCravingStrength: CravingStrength?,
    ) : CravingSupportViewState

    sealed interface CravingStrength {
        val emoji: String
        val labelRes: Int

        data object Low : CravingStrength {
            override val emoji: String = "\uD83D\uDE0C"
            override val labelRes: Int = com.shahzadian.baseui.R.string.craving_support_craving_strength_low
        }

        data object Middle : CravingStrength {
            override val emoji: String = "\uD83D\uDE10"
            override val labelRes: Int = com.shahzadian.baseui.R.string.craving_support_craving_strength_mid
        }

        data object Strong : CravingStrength {
            override val emoji: String = "\uD83D\uDE23"
            override val labelRes: Int = com.shahzadian.baseui.R.string.craving_support_craving_strength_strong
        }
    }
}

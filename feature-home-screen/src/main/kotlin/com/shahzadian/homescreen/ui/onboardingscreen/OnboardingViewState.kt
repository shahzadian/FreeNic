package com.shahzadian.homescreen.ui.onboardingscreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
sealed interface OnboardingViewState : ViewState {

    @Immutable
    data object Loading : OnboardingViewState

    @Immutable
    data class Data(
        val title: TextWrapper,
        val description: TextWrapper,
        val imageWrapper: ImageWrapper,
        val buttonText: TextWrapper,
    ) : OnboardingViewState
}

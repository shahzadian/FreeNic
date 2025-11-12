package com.shahzadian.homescreen.ui.welcomescreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
sealed interface WelcomeViewState : ViewState {

    /**
     * @param key unique key to define which view state is applied currently
     */
    @Immutable
    data class Data(
        val title: TextWrapper,
        val description: TextWrapper,
        val buttonText: TextWrapper,
        val image: ImageWrapper,
        val key: String,
    ) : WelcomeViewState
}

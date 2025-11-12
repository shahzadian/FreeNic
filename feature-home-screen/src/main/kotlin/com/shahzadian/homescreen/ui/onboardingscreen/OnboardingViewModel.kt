package com.shahzadian.homescreen.ui.onboardingscreen

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingViewModel :
    BaseViewModel<OnboardingViewState, OnboardingViewEvent, OnboardingViewCommand>() {

    override val initState: OnboardingViewState by lazyUnsafe {
        OnboardingViewState.Loading
    }

    init {
        viewModelScope.launch {
            delay(1000)
            setViewState(
                state = OnboardingViewState.Data(
                    title = TextWrapper.FromString(
                        text = "Some title",
                    ),
                    description = TextWrapper.FromString(
                        text = "Some desc",
                    ),
                    buttonText = TextWrapper.FromString(
                        text = "Some button text",
                    ),
                    imageWrapper = ImageWrapper.FromRes(
                        resId = com.shahzadian.homescreen.R.drawable.ic_welcome_screen
                    )
                )
            )
        }
    }

    override fun onViewEventHappened(event: OnboardingViewEvent) {

    }
}

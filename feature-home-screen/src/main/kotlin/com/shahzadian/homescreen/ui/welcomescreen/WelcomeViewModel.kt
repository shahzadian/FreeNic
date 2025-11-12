package com.shahzadian.homescreen.ui.welcomescreen

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

class WelcomeViewModel : BaseViewModel<WelcomeViewState, WelcomeViewEvent, WelcomeViewCommand>() {

    override val initState: WelcomeViewState by lazyUnsafe {
        WelcomeViewState.Data(
            title = TextWrapper.FromRes(
                resId = R.string.welcome_screen_title,
            ),
            description = TextWrapper.FromRes(
                resId = R.string.welcome_screen_desc,
            ),
            buttonText = TextWrapper.FromRes(
                resId = R.string.welcome_screen_button,
            ),
            image = ImageWrapper.FromRes(
                resId = com.shahzadian.homescreen.R.drawable.ic_welcome_screen,
            )
        )
    }

    override fun onViewEventHappened(event: WelcomeViewEvent) {

    }
}

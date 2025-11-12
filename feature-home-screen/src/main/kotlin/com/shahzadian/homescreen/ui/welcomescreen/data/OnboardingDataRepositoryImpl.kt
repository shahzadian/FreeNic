package com.shahzadian.homescreen.ui.welcomescreen.data

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.homescreen.ui.welcomescreen.domain.OnboardingData
import javax.inject.Inject

internal class OnboardingDataRepositoryImpl @Inject constructor() : OnboardingDataRepository {

    override suspend fun getAllData(): List<OnboardingData> {
//        TODO
        return listOf(
            OnboardingData(
                key = "sample",
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
                ),
            )
        )
    }
}

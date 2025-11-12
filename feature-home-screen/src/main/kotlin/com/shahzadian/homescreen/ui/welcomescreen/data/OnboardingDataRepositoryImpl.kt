package com.shahzadian.homescreen.ui.welcomescreen.data

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.homescreen.ui.welcomescreen.domain.OnboardingData
import javax.inject.Inject

internal class OnboardingDataRepositoryImpl @Inject constructor() : OnboardingDataRepository {

    private val image: ImageWrapper by lazyUnsafe {
        ImageWrapper.FromRes(
            resId = com.shahzadian.homescreen.R.drawable.ic_onboarding_1,
        )
    }

    override suspend fun getAllData(): List<OnboardingData> {
        return listOf(
            OnboardingData(
                title = TextWrapper.FromRes(
                    resId = R.string.onboarding_1_title,
                ),
                subtitle = TextWrapper.FromRes(
                    resId = R.string.onboarding_1_subtitle,
                ),
                image = image,
            ),
            OnboardingData(
                title = TextWrapper.FromRes(
                    resId = R.string.onboarding_2_title,
                ),
                subtitle = TextWrapper.FromRes(
                    resId = R.string.onboarding_2_subtitle,
                ),
                image = image,
            ),
            OnboardingData(
                title = TextWrapper.FromRes(
                    resId = R.string.onboarding_3_title,
                ),
                subtitle = TextWrapper.FromRes(
                    resId = R.string.onboarding_3_subtitle,
                ),
                image = image,
            ),
        )
    }
}

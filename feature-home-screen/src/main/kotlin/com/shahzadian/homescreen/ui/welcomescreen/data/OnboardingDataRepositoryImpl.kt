package com.shahzadian.homescreen.ui.welcomescreen.data

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.homescreen.ui.welcomescreen.domain.OnboardingData
import javax.inject.Inject

internal class OnboardingDataRepositoryImpl @Inject constructor() : OnboardingDataRepository {

    private val negativeImage: ImageWrapper by lazyUnsafe {
        ImageWrapper.FromRes(
            resId = com.shahzadian.homescreen.R.drawable.ic_onboarding_negative,
        )
    }

    private val positiveImage: ImageWrapper by lazyUnsafe {
        ImageWrapper.FromRes(
            resId = com.shahzadian.homescreen.R.drawable.ic_onboarding_positive,
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
                image = negativeImage,
                submitText = TextWrapper.FromRes(
                    resId = R.string.onboarding_1_submit,
                ),
            ),
            OnboardingData(
                title = TextWrapper.FromRes(
                    resId = R.string.onboarding_2_title,
                ),
                subtitle = TextWrapper.FromRes(
                    resId = R.string.onboarding_2_subtitle,
                ),
                image = negativeImage,
                submitText = TextWrapper.FromRes(
                    resId = R.string.onboarding_2_submit,
                ),
            ),
            OnboardingData(
                title = TextWrapper.FromRes(
                    resId = R.string.onboarding_3_title,
                ),
                subtitle = TextWrapper.FromRes(
                    resId = R.string.onboarding_3_subtitle,
                ),
                image = negativeImage,
                submitText = TextWrapper.FromRes(
                    resId = R.string.onboarding_3_submit,
                ),
            ),
            OnboardingData(
                title = TextWrapper.FromRes(
                    resId = R.string.onboarding_4_title,
                ),
                subtitle = TextWrapper.FromRes(
                    resId = R.string.onboarding_4_subtitle,
                ),
                image = positiveImage,
                submitText = TextWrapper.FromRes(
                    resId = R.string.onboarding_4_submit,
                ),
            ),
        )
    }
}

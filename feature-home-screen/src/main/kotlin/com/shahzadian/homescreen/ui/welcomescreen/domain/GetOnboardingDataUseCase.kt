package com.shahzadian.homescreen.ui.welcomescreen.domain

internal interface GetOnboardingDataUseCase {

    /**
     * @param currentDataKey pass null to get the default onboarding data
     * and existing key to get the next data
     * @return next onboarding data or null if no more data
     */
    suspend operator fun invoke(currentDataKey: String? = null) : OnboardingData?
}

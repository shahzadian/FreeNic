package com.shahzadian.welcomescreen.ui.welcomescreen.domain

internal interface GetOnboardingDataUseCase {

    suspend fun getNextData(): OnboardingData?
}

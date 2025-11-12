package com.shahzadian.homescreen.ui.welcomescreen.domain

internal interface GetOnboardingDataUseCase {

    suspend fun getNextData(): OnboardingData?
}

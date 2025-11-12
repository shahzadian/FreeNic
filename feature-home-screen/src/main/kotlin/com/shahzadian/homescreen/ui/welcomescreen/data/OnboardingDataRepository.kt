package com.shahzadian.homescreen.ui.welcomescreen.data

import com.shahzadian.homescreen.ui.welcomescreen.domain.OnboardingData

interface OnboardingDataRepository {

    suspend fun getAllData(): List<OnboardingData>
}

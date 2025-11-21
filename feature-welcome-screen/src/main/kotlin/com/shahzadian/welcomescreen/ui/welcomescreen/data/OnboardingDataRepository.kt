package com.shahzadian.welcomescreen.ui.welcomescreen.data

import com.shahzadian.welcomescreen.ui.welcomescreen.domain.OnboardingData

interface OnboardingDataRepository {

    suspend fun getAllData(): List<OnboardingData>
}

package com.shahzadian.welcomescreen.ui.welcomescreen.domain

import com.shahzadian.welcomescreen.ui.welcomescreen.data.OnboardingDataRepository
import javax.inject.Inject

internal class GetOnboardingDataUseCaseImpl @Inject constructor(
    private val onboardingDataRepository: OnboardingDataRepository
) : GetOnboardingDataUseCase {

    private var onboardingDataList: List<OnboardingData>? = null
    private var currentDataIndex: Int = -1

    override suspend fun getNextData(): OnboardingData? {
        val listToGetDataFrom = onboardingDataList ?: let {
            val data = onboardingDataRepository.getAllData()
            onboardingDataList = data
            data
        }
        if (listToGetDataFrom.isEmpty()) return null
        if (currentDataIndex == listToGetDataFrom.lastIndex) return null
        if (currentDataIndex++ == -1) return listToGetDataFrom.first()
        return listToGetDataFrom[currentDataIndex]
    }
}

package com.shahzadian.homescreen.ui.welcomescreen.domain

import com.shahzadian.homescreen.ui.welcomescreen.data.OnboardingDataRepository
import javax.inject.Inject

internal class GetOnboardingDataUseCaseImpl @Inject constructor(
    private val onboardingDataRepository: OnboardingDataRepository
) : GetOnboardingDataUseCase {

    private var onboardingDataList: List<OnboardingData>? = null

    override suspend fun invoke(currentDataKey: String?): OnboardingData? {
        val listToGetDataFrom = onboardingDataList ?: let {
            val data = onboardingDataRepository.getAllData()
            onboardingDataList = data
            data
        }
        if (listToGetDataFrom.isEmpty()) return null
        if (currentDataKey == null) return listToGetDataFrom.first()
        val currentIndex = listToGetDataFrom.indexOfFirst { it.key == currentDataKey }
        if (currentIndex == -1 || currentIndex == listToGetDataFrom.lastIndex) return null
        return listToGetDataFrom[currentIndex + 1]
    }
}

package com.shahzadian.dailycheckin.domain

import com.shahzadian.dailycheckin.data.DailyCheckInRepository
import javax.inject.Inject

interface AddDailyCheckInStateUseCase {

    suspend operator fun invoke(dailyCheckInState: DailyCheckInState)
}

internal class AddDailyCheckInStateUseCaseImpl @Inject constructor(
    private val dailyCheckInRepository: DailyCheckInRepository,
) : AddDailyCheckInStateUseCase {

    override suspend fun invoke(dailyCheckInState: DailyCheckInState) {
        dailyCheckInRepository.add(dailyCheckInState)
    }
}
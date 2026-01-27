package com.shahzadian.dailycheckin.domain

import com.shahzadian.dailycheckin.data.DailyCheckInRepository
import javax.inject.Inject

interface ClearAllDailyCheckInsUseCase {

    suspend operator fun invoke()
}

internal class ClearAllDailyCheckInsUseCaseImpl @Inject constructor(
    private val dailyCheckInRepository: DailyCheckInRepository,
) : ClearAllDailyCheckInsUseCase {

    override suspend fun invoke() {
        dailyCheckInRepository.clearAll()
    }
}
package com.shahzadian.dailycheckin.domain

import com.shahzadian.dailycheckin.data.DailyCheckInRepository
import java.time.LocalDate
import javax.inject.Inject

interface GetDailyCheckInForDayUseCase {

    suspend operator fun invoke(date: LocalDate): DailyCheckInState?
}

internal class GetDailyCheckInForDayUseCaseImpl @Inject constructor(
    private val dailyCheckInRepository: DailyCheckInRepository,
) : GetDailyCheckInForDayUseCase {

    override suspend fun invoke(date: LocalDate): DailyCheckInState? {
        return dailyCheckInRepository.getForDay(date)
    }
}

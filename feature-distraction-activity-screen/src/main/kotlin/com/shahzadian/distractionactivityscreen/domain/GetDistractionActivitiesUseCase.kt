package com.shahzadian.distractionactivityscreen.domain

import com.shahzadian.distractionactivityscreen.DistractionActivityType
import javax.inject.Inject

internal interface GetDistractionActivitiesUseCase {

    suspend operator fun invoke(): List<DistractionActivityType>
}

internal class GetDistractionActivitiesUseCaseImpl @Inject constructor() :
    GetDistractionActivitiesUseCase {

    override suspend fun invoke(): List<DistractionActivityType> {
        return listOf(
            DistractionActivityType.GuessShape
        )
    }
}

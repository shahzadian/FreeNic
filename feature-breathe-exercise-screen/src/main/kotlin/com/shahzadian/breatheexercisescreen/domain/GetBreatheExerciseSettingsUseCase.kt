package com.shahzadian.breatheexercisescreen.domain

import javax.inject.Inject

interface GetBreatheExerciseSettingsUseCase {

    suspend operator fun invoke(): BreatheExerciseSettings
}

internal class GetBreatheExerciseSettingsUseCaseImpl @Inject constructor() : GetBreatheExerciseSettingsUseCase {

    override suspend fun invoke(): BreatheExerciseSettings {
        return BreatheExerciseSettings(
            inhaleSeconds = 4,
            holdSeconds = 7,
            exhaleSeconds = 8,
        )
    }
}

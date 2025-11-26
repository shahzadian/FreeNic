package com.shahzadian.breatheexercisescreen.domain

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import javax.inject.Inject

interface GetBreatheExerciseSettingsUseCase {

    suspend operator fun invoke(): BreatheExerciseSettings
}

internal class GetBreatheExerciseSettingsUseCaseImpl @Inject constructor() :
    GetBreatheExerciseSettingsUseCase {

    override suspend fun invoke(): BreatheExerciseSettings {
        return BreatheExerciseSettings(
            inhaleSeconds = 4,
            holdSeconds = 7,
            exhaleSeconds = 8,
            cyclesCount = 3,
            helpingTexts = listOf(
                TextWrapper.FromRes(R.string.breathe_exercise_helping_text_body_is_relaxing),
                TextWrapper.FromRes(R.string.breathe_exercise_helping_text_breathe_calmly),
                TextWrapper.FromRes(R.string.breathe_exercise_helping_text_slow_and_steady),
                TextWrapper.FromRes(R.string.breathe_exercise_helping_text_stay_with_the_rhythm),
                TextWrapper.FromRes(R.string.breathe_exercise_helping_text_you_are_doing_great),
            )
        )
    }
}

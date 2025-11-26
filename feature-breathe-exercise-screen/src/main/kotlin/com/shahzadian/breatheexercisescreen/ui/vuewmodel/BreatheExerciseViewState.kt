package com.shahzadian.breatheexercisescreen.ui.vuewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface BreatheExerciseViewState : ViewState {

    @Immutable
    data object Loading : BreatheExerciseViewState

    @Immutable
    data class Process(
        val inhaleSeconds: Int,
        val holdSeconds: Int,
        val exhaleSeconds: Int,
        val cyclesCount: Int,
        val currentCycle: Int,
        val helpingTexts: List<TextWrapper>,
        val exerciseState: ExerciseState,
    ) : BreatheExerciseViewState

    @Immutable
    enum class ExerciseState {
        READY_FOR_EXERCISE, INHALE, HOLD, EXHALE,
    }
}

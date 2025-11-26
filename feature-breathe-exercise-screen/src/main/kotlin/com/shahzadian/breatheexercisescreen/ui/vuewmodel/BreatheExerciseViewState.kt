package com.shahzadian.breatheexercisescreen.ui.vuewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface BreatheExerciseViewState : ViewState {

    @Immutable
    data object Loading : BreatheExerciseViewState

    @Immutable
    sealed interface Data : BreatheExerciseViewState {

        val inhaleSeconds: Int
        val holdSeconds: Int
        val exhaleSeconds: Int
        val cyclesCount: Int
        val currentCycle: Int

        @Immutable
        data class ReadyForExercise(
            override val inhaleSeconds: Int,
            override val holdSeconds: Int,
            override val exhaleSeconds: Int,
            override val cyclesCount: Int,
            override val currentCycle: Int,
        ) : Data

        @Immutable
        data class Inhale(
            override val inhaleSeconds: Int,
            override val holdSeconds: Int,
            override val exhaleSeconds: Int,
            override val cyclesCount: Int,
            override val currentCycle: Int,
        ) : Data

        data class Hold(
            override val inhaleSeconds: Int,
            override val holdSeconds: Int,
            override val exhaleSeconds: Int,
            override val cyclesCount: Int,
            override val currentCycle: Int,
        ) : Data

        data class Exhale(
            override val inhaleSeconds: Int,
            override val holdSeconds: Int,
            override val exhaleSeconds: Int,
            override val cyclesCount: Int,
            override val currentCycle: Int,
        ) : Data
    }
}

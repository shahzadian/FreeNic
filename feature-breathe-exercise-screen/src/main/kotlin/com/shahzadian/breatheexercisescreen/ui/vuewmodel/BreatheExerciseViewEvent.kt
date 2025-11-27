package com.shahzadian.breatheexercisescreen.ui.vuewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface BreatheExerciseViewEvent : ViewEvent {

    data object OnCircleClick : BreatheExerciseViewEvent

    data object OnInhaleFinish : BreatheExerciseViewEvent

    data object OnHoldFinish : BreatheExerciseViewEvent

    data object OnExhaleFinish : BreatheExerciseViewEvent

    data object OnFinishClick : BreatheExerciseViewEvent
}

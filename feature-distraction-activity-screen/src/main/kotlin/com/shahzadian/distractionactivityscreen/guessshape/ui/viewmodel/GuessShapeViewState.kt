package com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface GuessShapeViewState : ViewState {

    @Immutable
    data object Loading : GuessShapeViewState

    @Immutable
    data class Process(val activityState: ActivityState) : GuessShapeViewState

    @Immutable
    enum class ActivityState {
        READY_FOR_ACTIVITY, ACTIVITY_IN_PROGRESS
    }
}

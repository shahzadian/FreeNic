package com.shahzadian.distractionactivityscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.shahzadian.distractionactivityscreen.DistractionActivityType

@Immutable
internal sealed interface DistractionActivityViewState : ViewState {

    data object Loading : DistractionActivityViewState

    @Immutable
    data class Data(
        val activityType: DistractionActivityType,
    ) : DistractionActivityViewState
}

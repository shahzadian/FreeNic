package com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface GuessShapeViewEvent : ViewEvent {

    data object OnShapeShowTimeExpired : GuessShapeViewEvent
}

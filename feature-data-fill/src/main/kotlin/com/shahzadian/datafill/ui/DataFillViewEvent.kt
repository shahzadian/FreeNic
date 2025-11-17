package com.shahzadian.datafill.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface DataFillViewEvent : ViewEvent {

    data class OnNameChanged(val name: String) : DataFillViewEvent
    data class OnAgeChanged(val age: String) : DataFillViewEvent
}

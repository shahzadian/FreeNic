package com.shahzadian.datafill.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface DataFillViewEvent : ViewEvent {

    data class OnNameChanged(val name: String) : DataFillViewEvent
    data class OnAgeChanged(val age: String) : DataFillViewEvent
    data class OnCigarettesPerDayChanged(val cigarettesPerDay: String) : DataFillViewEvent
    data class OnPackPriceChanged(val price: String, val currency: String) : DataFillViewEvent
    data class OnQuitDateChanged(val quitDateMillis: Long) : DataFillViewEvent
    data object OnSubmitNext : DataFillViewEvent
    data object OnBack : DataFillViewEvent
}

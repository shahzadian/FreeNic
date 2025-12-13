package com.shahzadian.profilescreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent
import java.time.LocalDate

@Immutable
internal sealed interface ProfileViewEvent : ViewEvent {

    @Immutable
    data object OnPremiumClicked : ProfileViewEvent

    @Immutable
    data class OnQuitDateSelected(
        val millis: Long,
    ) : ProfileViewEvent

    @Immutable
    data class OnNameEdited(val name: String) : ProfileViewEvent

    @Immutable
    data class OnAgeEdited(val age: Int) : ProfileViewEvent

    @Immutable
    data class OnCigsPerDayEdited(val cigarettesPerDay: Int) : ProfileViewEvent

    @Immutable
    data class OnPriceEdited(val price: Float) : ProfileViewEvent
}

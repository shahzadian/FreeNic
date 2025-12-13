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
}

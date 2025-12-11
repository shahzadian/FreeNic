package com.shahzadian.profilescreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface ProfileViewEvent : ViewEvent {

    @Immutable
    data object OnPremiumClicked : ProfileViewEvent
}

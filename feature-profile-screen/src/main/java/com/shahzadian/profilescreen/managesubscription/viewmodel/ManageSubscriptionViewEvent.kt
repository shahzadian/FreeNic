package com.shahzadian.profilescreen.managesubscription.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface ManageSubscriptionViewEvent : ViewEvent {

    @Immutable
    data object OnBackIconClicked : ManageSubscriptionViewEvent
}

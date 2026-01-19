package com.shahzadian.profilescreen.managesubscription.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface ManageSubscriptionViewState : ViewState {

    @Immutable
    data object Loading : ManageSubscriptionViewState


    @Immutable
    data object Data : ManageSubscriptionViewState
}

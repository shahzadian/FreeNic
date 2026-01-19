package com.shahzadian.profilescreen.managesubscription.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface ManageSubscriptionViewCommand : ViewCommand {

    @Immutable
    data object NavigateBack : ManageSubscriptionViewCommand
}

package com.shahzadian.profilescreen.managesubscription.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface ManageSubscriptionViewState : ViewState {

    @Immutable
    data object Loading : ManageSubscriptionViewState


    @Immutable
    data class Data(
        val currentPlanTitle: TextWrapper,
        val activeUntil: String,
        val paidFeatures: List<TextWrapper>,
    ) : ManageSubscriptionViewState
}

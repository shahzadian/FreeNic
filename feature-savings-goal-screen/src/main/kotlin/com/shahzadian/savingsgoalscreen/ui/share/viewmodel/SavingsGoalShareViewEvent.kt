package com.shahzadian.savingsgoalscreen.ui.share.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface SavingsGoalShareViewEvent : ViewEvent {

    @Immutable
    data class OnShareClicked(
        val selectedTemplateId: String,
    ) : SavingsGoalShareViewEvent
}
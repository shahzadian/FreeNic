package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface GoalsViewEvent : ViewEvent {

    @Immutable
    data object OnSavingGoalsUpgradeClicked : GoalsViewEvent

    @Immutable
    data object OnShowMoreAchievementsClicked : GoalsViewEvent

    @Immutable
    data class OnAchievementShareClicked(
        val id: String,
    ) : GoalsViewEvent
}

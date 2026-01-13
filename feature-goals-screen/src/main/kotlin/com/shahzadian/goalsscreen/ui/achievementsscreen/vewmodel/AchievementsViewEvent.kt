package com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface AchievementsViewEvent : ViewEvent {

    @Immutable
    data class OnAchievementShareClicked(
        val achievementId: String,
    ) : AchievementsViewEvent

    @Immutable
    data object OnBackIconClicked : AchievementsViewEvent
}

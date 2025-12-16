package com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel

import com.freenic.ui.vm.ViewEvent

internal sealed interface AchievementsViewEvent : ViewEvent {

    data class OnAchievementShareClicked(
        val achievementId: String,
    ) : AchievementsViewEvent
}

package com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface AchievementShareViewEvent : ViewEvent {

    data class OnAchievementShareClicked(
        val templateId: String,
    ) : AchievementShareViewEvent
}

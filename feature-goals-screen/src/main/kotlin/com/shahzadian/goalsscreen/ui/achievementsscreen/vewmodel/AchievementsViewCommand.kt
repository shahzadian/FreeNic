package com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface AchievementsViewCommand : ViewCommand {

    @Immutable
    data class NavigateToAchievementShareScreen(val id: String) : AchievementsViewCommand

    @Immutable
    data object NavigateBack : AchievementsViewCommand
}

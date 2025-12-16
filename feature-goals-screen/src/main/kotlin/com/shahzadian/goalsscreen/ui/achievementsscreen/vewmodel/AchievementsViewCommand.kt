package com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel

import com.freenic.ui.vm.ViewCommand

internal sealed interface AchievementsViewCommand : ViewCommand {

    data class NavigateToAchievementShareScreen(val id: String) : AchievementsViewCommand
}

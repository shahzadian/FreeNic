package com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface AchievementShareViewCommand : ViewCommand {

    @Immutable
    data class ShowError(val message: TextWrapper): AchievementShareViewCommand

    @Immutable
    data object NavigateBack : AchievementShareViewCommand
}

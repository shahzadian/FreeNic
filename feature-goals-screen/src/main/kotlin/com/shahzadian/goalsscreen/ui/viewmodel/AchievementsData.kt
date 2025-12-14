package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.shahzadian.goalsscreen.ui.AchievementsConst

@Immutable
internal data class AchievementsData(
    val achievements: List<AchievementUiModel>,
    val shouldShowMoreAchievementsButton: Boolean,
    val achievementsPerRow: Int = AchievementsConst.MAX_ACHIEVEMENTS_PER_ROW,
)
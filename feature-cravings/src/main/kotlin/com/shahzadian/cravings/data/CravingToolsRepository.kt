package com.shahzadian.cravings.data

import com.freenic.ui.AppScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.breatheexercisescreen.domain.GetBreatheExerciseSettingsUseCase
import com.shahzadian.cravings.domain.CravingTool
import javax.inject.Inject

internal interface CravingToolsRepository {

    suspend fun getCravingTools(): List<CravingTool>
}

internal class CravingToolsRepositoryImpl @Inject constructor(
    private val getBreatheExerciseSettingsUseCase: GetBreatheExerciseSettingsUseCase,
) : CravingToolsRepository {

    override suspend fun getCravingTools(): List<CravingTool> {
        val breatheExerciseSettings = getBreatheExerciseSettingsUseCase.invoke()
        return listOf(
            CravingTool(
                id = "breathe_exercise",
                name = TextWrapper.FromRes(
                    R.string.cravings_tool_breathing_title,
                    listOf(
                        breatheExerciseSettings.inhaleSeconds,
                        breatheExerciseSettings.holdSeconds,
                        breatheExerciseSettings.exhaleSeconds
                    )
                ),
                description = TextWrapper.FromRes(
                    R.string.cravings_tool_breathing_subtitle
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.cravings.R.drawable.ic_cravings_tool_breathe,
                ),
                behavior = CravingTool.Behavior.NavigateToScreen(AppScreen.BreatheExerciseScreen),
            ),
            CravingTool(
                id = "distraction_activity",
                name = TextWrapper.FromRes(
                    R.string.cravings_tool_distraction_title,
                ),
                description = TextWrapper.FromRes(
                    R.string.cravings_tool_distraction_subtitle,
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.cravings.R.drawable.ic_cravings_tool_distraction
                ),
                behavior = CravingTool.Behavior.NavigateToScreen(
                    screen = AppScreen.DistractionActivityScreen,
                )
            ),
            CravingTool(
                id = "motivational_notes",
                name = TextWrapper.FromRes(
                    R.string.craving_tool_motivational_notes_title
                ),
                description = TextWrapper.FromRes(
                    R.string.craving_tool_motivational_notes_subtitle
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.cravings.R.drawable.ic_cravings_tool_motivational_notes
                ),
                behavior = CravingTool.Behavior.NavigateToScreen(AppScreen.MotivationalNotesScreen)
            )
        )
    }
}

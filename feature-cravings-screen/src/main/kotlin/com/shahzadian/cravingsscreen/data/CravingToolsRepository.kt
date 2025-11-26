package com.shahzadian.cravingsscreen.data

import com.freenic.ui.AppScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.breatheexercisescreen.domain.GetBreatheExerciseSettingsUseCase
import com.shahzadian.cravingsscreen.domain.CravingTool
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
                name = TextWrapper.FromRes(
                    R.string.cravings_tool_breathing_title,
                    listOf(breatheExerciseSettings.inhaleSeconds, breatheExerciseSettings.holdSeconds, breatheExerciseSettings.exhaleSeconds)
                ),
                description = TextWrapper.FromRes(
                    R.string.cravings_tool_breathing_subtitle
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.cravingsscreen.R.drawable.ic_cravings_tool_breathe,
                ),
                behavior = CravingTool.Behavior.NavigateToScreen(AppScreen.BreatheExerciseScreen),
            ),
            CravingTool(
                name = TextWrapper.FromRes(
                    R.string.cravings_tool_relaxation_title,
                ),
                description = TextWrapper.FromRes(
                    R.string.cravings_tool_relaxation_subtitle,
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.cravingsscreen.R.drawable.ic_cravings_tool_relaxation
                )
            ),
            CravingTool(
                name = TextWrapper.FromRes(
                    R.string.cravings_tool_distraction_title,
                ),
                description = TextWrapper.FromRes(
                    R.string.cravings_tool_distraction_subtitle,
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.cravingsscreen.R.drawable.ic_cravings_tool_distraction
                )
            ),
            CravingTool(
                name = TextWrapper.FromRes(
                    R.string.cravings_tool_water_title,
                ),
                description = TextWrapper.FromRes(
                    R.string.cravings_tool_water_subtitle,
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.cravingsscreen.R.drawable.ic_cravings_tool_water
                )
            ),
            CravingTool(
                name = TextWrapper.FromRes(
                    R.string.cravings_tool_mini_mindfulness_title,
                ),
                description = TextWrapper.FromRes(
                    R.string.cravings_tool_mini_mindfulness_subtitle,
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.cravingsscreen.R.drawable.ic_cravings_tool_mindfulness
                )
            ),
        )
    }
}

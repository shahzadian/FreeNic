package com.shahzadian.savingsgoalscreen.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
data class SavingsGoalCategoryUiModel(
    val id: String,
    val name: TextWrapper,
    val icon: ImageWrapper,
)

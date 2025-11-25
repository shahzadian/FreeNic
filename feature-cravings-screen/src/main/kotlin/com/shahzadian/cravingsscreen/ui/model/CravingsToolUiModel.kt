package com.shahzadian.cravingsscreen.ui.model

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class CravingToolsUiModel(
    val tools: List<CravingsToolUiModel>,
    val showAllButton: Boolean,
    val onAllButtonClick: () -> Unit,
)

@Immutable
internal data class CravingsToolUiModel(
    val icon: ImageWrapper,
    val title: TextWrapper,
    val subtitle: TextWrapper,
    val onClick: () -> Unit,
)
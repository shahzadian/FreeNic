package com.shahzadian.mainscreen.ui.model

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class MainScreenUiModel(
    val items: List<MainScreenItemUiModel>,
)

internal data class MainScreenItemUiModel(
    val title: TextWrapper,
    val icon: ImageWrapper,
    val screen: AppScreen,
    val isPaid: Boolean,
)

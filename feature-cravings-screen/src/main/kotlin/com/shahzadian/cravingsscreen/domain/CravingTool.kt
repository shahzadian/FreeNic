package com.shahzadian.cravingsscreen.domain

import com.freenic.ui.AppScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

internal class CravingTool(
    val name: TextWrapper,
    val description: TextWrapper,
    val icon: ImageWrapper,
    val behavior: Behavior = Behavior.None,
) {

    internal sealed interface Behavior {
        data object None : Behavior
        data class NavigateToScreen(val screen: AppScreen) : Behavior
    }
}

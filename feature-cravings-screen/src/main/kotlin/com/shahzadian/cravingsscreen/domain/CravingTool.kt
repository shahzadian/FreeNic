package com.shahzadian.cravingsscreen.domain

import com.freenic.ui.AppScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

class CravingTool(
    val id: String,
    val name: TextWrapper,
    val description: TextWrapper,
    val icon: ImageWrapper,
    val behavior: Behavior = Behavior.None,
) {

    sealed interface Behavior {
        data object None : Behavior
        data class NavigateToScreen(val screen: AppScreen) : Behavior
    }
}

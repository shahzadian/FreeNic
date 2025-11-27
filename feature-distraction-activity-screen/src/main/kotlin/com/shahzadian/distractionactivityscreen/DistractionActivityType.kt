package com.shahzadian.distractionactivityscreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe

@Immutable
internal sealed interface DistractionActivityType {

    val appScreen: AppScreen

    @Immutable
    data object GuessShape : DistractionActivityType {

        override val appScreen: AppScreen by lazyUnsafe {
            AppScreen.GuessShapeScreen
        }
    }
}

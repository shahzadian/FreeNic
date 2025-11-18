package com.freenic.ui.wrapper

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface ImageWrapper {

    data class FromRes(
        @param:DrawableRes val resId: Int,
    ) : ImageWrapper
}
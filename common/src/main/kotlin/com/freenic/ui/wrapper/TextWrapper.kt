package com.freenic.ui.wrapper

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface TextWrapper {

    @Immutable
    data class FromRes(
        @param:StringRes val resId: Int,
        val args: List<Any> = emptyList(),
    ) : TextWrapper

    data class FromPlural(
        @param:PluralsRes val resId: Int,
        val quantity: Int,
        val args: List<Any> = emptyList(),
    ) : TextWrapper

    @Immutable
    data class FromString(val text: String) : TextWrapper
}

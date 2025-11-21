package com.shahzadian.dashboard.ui.healthprogress.model

import androidx.compose.runtime.Immutable
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal data class HealthProgressUiModel(
    val items: List<HealthProgressItemUiModel>,
) {

    @Immutable
    data class HealthProgressItemUiModel(
        val label: TextWrapper,
        val progress: Int,
        val icon: ImageWrapper,
    )
}

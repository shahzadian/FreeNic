package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface InsightsViewState : ViewState {

    @Immutable
    data object Loading : InsightsViewState

    @Immutable
    data class Data(
        val chartBlocks: List<ChartBlockUiModel>,
        val selectedBlockId: String,
    ) : InsightsViewState

    @Immutable
    data class ChartBlockUiModel(
        val id: String,
        val title: TextWrapper,
        val icon: ImageWrapper,
        val charts: List<ChartUiModel>,
    )

    sealed interface ChartUiModel {
        @Immutable
        data class TitleAndPercentsChartUiModel(
            val id: String,
            val title: TextWrapper,
            val subtitle: TextWrapper,
            val icon: ImageWrapper,
            val percents: Int,
            val badge: BadgeUiModel,
        ) : ChartUiModel {

            @Immutable
            data class BadgeUiModel(
                val text: TextWrapper,
            )
        }

        @Immutable
        data class LungsRecoveryUiModel(
            val percents: Int,
        ) : ChartUiModel

        data class HeartRecoveryUiModel(
            val percents: Int,
        ) : ChartUiModel
    }
}

package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal sealed interface InsightsViewState : ViewState {

    @Immutable
    data object Loading : InsightsViewState

    @Immutable
    data class Preview(
        val previewRewardState: PreviewRewardState,
    ) : InsightsViewState

    @Immutable
    data class Data(
        val accessType: InsightsAccessType,
        val chartBlocks: List<ChartBlockUiModel>,
        val selectedBlockId: String,
    ) : InsightsViewState

    @Immutable
    sealed interface PreviewRewardState {
        data object Default : PreviewRewardState
        data object Loading : PreviewRewardState
    }

    @Immutable
    data class ChartBlockUiModel(
        val id: String,
        val title: TextWrapper,
        val icon: ImageWrapper,
        val charts: List<ChartUiModel>,
        val shouldShowDisclaimer: Boolean,
        val isScrollable: Boolean,
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

        data class SlipCasesChartUiModel(
            val items: ImmutableList<ChartItem>,
        ) : ChartUiModel {
            data class ChartItem(
                val label: String,
                val value: Int,
            )
        }
    }

    @Immutable
    sealed interface InsightsAccessType {
        data object Permanent : InsightsAccessType
        data object UntilTheDayEnd : InsightsAccessType
        data object NoAccess : InsightsAccessType
    }
}

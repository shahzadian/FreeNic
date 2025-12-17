package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.insightsscreen.R

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
        val charts: List<TitleAndPercentsChartUiModel>,
    )

    @Immutable
    data class TitleAndPercentsChartUiModel(
        val title: TextWrapper,
        val subtitle: TextWrapper,
        val hintUiModel: HintUiModel,
    ) {

        @Immutable
        sealed interface HintUiModel {

            val icon: ImageWrapper
            val text: TextWrapper

            @Immutable
            data object MedicalResearch : HintUiModel {

                override val icon: ImageWrapper by lazyUnsafe {
                    ImageWrapper.FromRes(R.drawable.ic_insights_title_and_percents_chart_hint_medical)
                }

                override val text: TextWrapper by lazyUnsafe {
                    TextWrapper.FromRes(com.shahzadian.baseui.R.string.insights_hint_medical)
                }
            }
        }
    }
}

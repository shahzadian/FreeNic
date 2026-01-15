package com.shahzadian.cravingsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventConst.CRAVINGS_TOOL_PARAM_CRAVING_TOOL_ID
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.cravingsscreen.domain.CravingTool
import com.shahzadian.cravingsscreen.domain.GetCravingToolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CravingsViewModel @Inject constructor(
    private val getCravingToolsUseCase: GetCravingToolsUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<CravingsViewState, CravingsViewEvent, CravingsViewCommand>() {

    override val initState: CravingsViewState by lazyUnsafe {
        CravingsViewState.Loading
    }

    init {
        viewModelScope.launch {
            val tools = getCravingToolsUseCase()
            setViewState(tools.toViewState())
        }
    }

    override fun onViewEventHappened(event: CravingsViewEvent) {
//
    }

    private fun List<CravingTool>.toViewState(): CravingsViewState.Data {
        return CravingsViewState.Data(
            tools = this.take(3).map { tool ->
                CravingsViewState.Data.CravingsToolUiModel(
                    title = tool.name,
                    subtitle = tool.description,
                    icon = tool.icon,
                    onClick = {
                        analyticsEventSender.send(
                            AnalyticsEvent(
                                name = AnalyticsEventConst.CRAVINGS_TOOL_TAP,
                                params = mapOf(
                                    CRAVINGS_TOOL_PARAM_CRAVING_TOOL_ID to tool.id.asAnalyticsProperty()
                                )
                            )
                        )
                        when (tool.behavior) {
                            is CravingTool.Behavior.NavigateToScreen -> {
                                viewModelScope.launch {
                                    sendViewCommand(CravingsViewCommand.NavigateTo(tool.behavior.screen))
                                }
                            }

                            is CravingTool.Behavior.None -> {}
                        }
                    },
                )
            },
            showAllButton = this.size > 3,
            onAllButtonClick = {}
        )
    }
}

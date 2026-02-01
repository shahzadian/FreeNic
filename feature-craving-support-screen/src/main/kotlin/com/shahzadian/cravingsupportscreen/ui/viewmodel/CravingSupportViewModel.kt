package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.cravings.domain.CravingTool
import com.shahzadian.cravings.domain.GetCravingToolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CravingSupportViewModel @Inject constructor(
    private val getCravingToolsUseCase: GetCravingToolsUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<CravingSupportViewState, CravingSupportViewEvent, CravingSupportViewCommand>() {

    override val initState: CravingSupportViewState by lazyUnsafe {
        CravingSupportViewState.Loading
    }

    private var selectedCravingStrength: CravingSupportViewState.CravingStrength? = null

    private var _cravingTools: List<CravingTool>? = null

    private var screenEnterTime: Long = System.currentTimeMillis()

    init {
        setData()
    }

    override fun onViewEventHappened(event: CravingSupportViewEvent) {
        when (event) {
            is CravingSupportViewEvent.OnBackIconClicked -> {
                onBackIconClicked()
            }

            is CravingSupportViewEvent.OnCravingStrengthSelected -> {
                onCravingStrengthSelected(event.strength)
            }

            is CravingSupportViewEvent.OnActivityClicked -> {
                onActivityClicked(event.id)
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            setViewState(
                CravingSupportViewState.Data(
                    activities = getCravingTools()
                        .mapNotNull { tool ->
                            if (tool.behavior !is CravingTool.Behavior.NavigateToScreen) return@mapNotNull null
                            tool.toUiModel()
                        }
                        .toImmutableList(),
                    selectedCravingStrength = selectedCravingStrength,
                    otherActivities = getCravingTools()
                        .mapNotNull { tool ->
                            if (tool.behavior !is CravingTool.Behavior.None) return@mapNotNull null
                            tool.toUiModel()
                        }
                        .toImmutableList()
                )
            )
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(CravingSupportViewCommand.NavigateBack)
        }
    }

    private fun onCravingStrengthSelected(strength: CravingSupportViewState.CravingStrength) {
        selectedCravingStrength = strength
        setData()
        if (shouldFixCraving()) {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.CRAVING_SUPPORT_STRENGTH_SELECTED,
                    params = mapOf(
                        AnalyticsEventConst.CRAVING_SUPPORT_STRENGTH_SELECTED_PARAM_STRENGTH_LABEL to strength.analyticsLabel.asAnalyticsProperty()
                    )
                )
            )
        }
    }

    private fun onActivityClicked(id: String) {
        viewModelScope.launch {
            getCravingTools().firstOrNull { it.id == id }?.let { tool ->
                when (tool.behavior) {
                    is CravingTool.Behavior.NavigateToScreen -> {
                        sendViewCommand(
                            CravingSupportViewCommand.NavigateTo(
                                screen = (tool.behavior as CravingTool.Behavior.NavigateToScreen).screen,
                            )
                        )
                    }

                    is CravingTool.Behavior.None -> {}
                }
            }
            if (shouldFixCraving()) {
                analyticsEventSender.send(
                    AnalyticsEvent(
                        name = AnalyticsEventConst.CRAVING_SUPPORT_ACTIVITY_TAP,
                        params = mapOf(
                            AnalyticsEventConst.CRAVING_SUPPORT_ACTIVITY_TAP_PARAM_ACTIVITY_ID to id.asAnalyticsProperty(),
                        )
                    )
                )
            }
        }
    }

    private suspend fun getCravingTools(): List<CravingTool> {
        return _cravingTools ?: let {
            val tools = getCravingToolsUseCase()
            _cravingTools = tools
            return@let _cravingTools!!
        }
    }

    private fun shouldFixCraving(): Boolean {
        return System.currentTimeMillis() - screenEnterTime > SCREEN_TIME_FOR_CRAVING_FIX
    }

    private fun CravingTool.toUiModel(): CravingSupportViewState.CravingSupportActivityUiModel {
        return CravingSupportViewState.CravingSupportActivityUiModel(
            id = this.id,
            name = this.name,
            description = this.description,
            icon = this.icon,
        )
    }

    private companion object {
        const val SCREEN_TIME_FOR_CRAVING_FIX = 5000L
    }
}

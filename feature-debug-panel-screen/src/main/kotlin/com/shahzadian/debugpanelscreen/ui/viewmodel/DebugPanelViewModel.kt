package com.shahzadian.debugpanelscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.debugpanelscreen.domain.DebugPanelParams
import com.shahzadian.debugpanelscreen.domain.DebugPanelParamsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DebugPanelViewModel @Inject constructor(
    private val debugPanelParamsHelper: DebugPanelParamsHelper,
) : BaseViewModel<DebugPanelViewState, DebugPanelViewEvent, DebugPanelViewCommand>() {

    override val initState: DebugPanelViewState by lazyUnsafe { DebugPanelViewState.Loading }

    init {
        setData()
    }

    override fun onViewEventHappened(event: DebugPanelViewEvent) {
        when (event) {
            is DebugPanelViewEvent.OnHasPaidSubscriptionSwitched -> {
                viewModelScope.launch {
                    val panel =
                        debugPanelParamsHelper.onHasPaidSubscriptionSwitched(event.isSwitchOn)
                    setData(panel)
                }
            }
        }
    }

    private fun setData(
        debugPanelParams: DebugPanelParams? = null,
    ) {
        viewModelScope.launch {
            val params = debugPanelParams ?: debugPanelParamsHelper.getParams() ?: return@launch
            val hasPaidSubscription = params.hasPaidSubscription
            setViewState(
                DebugPanelViewState.Data(
                    hasPaidSubscription = hasPaidSubscription
                )
            )
        }
    }
}

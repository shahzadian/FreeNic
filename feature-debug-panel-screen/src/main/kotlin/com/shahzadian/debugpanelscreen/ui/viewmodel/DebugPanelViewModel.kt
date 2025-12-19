package com.shahzadian.debugpanelscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DebugPanelViewModel @Inject constructor(
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
) : BaseViewModel<DebugPanelViewState, DebugPanelViewEvent, DebugPanelViewCommand>() {

    override val initState: DebugPanelViewState by lazyUnsafe { DebugPanelViewState.Loading }

    init {
        setData()
    }

    override fun onViewEventHappened(event: DebugPanelViewEvent) {

    }

    private fun setData() {
        viewModelScope.launch {
            val currentSubscriptionPlan = getUserCurrentSubscriptionUseCase()
            setViewState(
                DebugPanelViewState.Data(
                    hasPaidSubscription = currentSubscriptionPlan != null
                )
            )
        }
    }
}
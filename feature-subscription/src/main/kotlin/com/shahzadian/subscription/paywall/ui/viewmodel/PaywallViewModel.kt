package com.shahzadian.subscription.paywall.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCase
import com.shahzadian.subscription.domain.PaidFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PaywallViewModel @Inject constructor(
    private val getPaidFeaturesUseCase: GetPaidFeaturesUseCase,
) : BaseViewModel<PaywallViewState, PaywallViewEvent, PaywallViewCommand>() {

    override val initState: PaywallViewState by lazyUnsafe { PaywallViewState.Loading }

    init {
        setDataViewState()
    }

    override fun onViewEventHappened(event: PaywallViewEvent) {

    }

    private fun setDataViewState() {
        viewModelScope.launch {
            val paidFeatures = getPaidFeaturesUseCase().map { it.toUiModel() }
            setViewState(
                PaywallViewState.Data(
                    paidFeatures = paidFeatures,
                )
            )
        }
    }

    private fun PaidFeature.toUiModel(): PaywallViewState.PaidFeatureUiModel {
        return PaywallViewState.PaidFeatureUiModel(
            title = this.name,
            subtitle = this.description,
            icon = this.icon,
        )
    }
}

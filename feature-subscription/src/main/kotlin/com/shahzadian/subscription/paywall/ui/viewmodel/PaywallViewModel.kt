package com.shahzadian.subscription.paywall.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCase
import com.shahzadian.subscription.domain.GetSubscriptionPlansUseCase
import com.shahzadian.subscription.domain.PaidFeature
import com.shahzadian.subscription.paywall.ui.SubscriptionPlanMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PaywallViewModel @Inject constructor(
    private val getPaidFeaturesUseCase: GetPaidFeaturesUseCase,
    private val getSubscriptionPlansUseCase: GetSubscriptionPlansUseCase,
    private val subscriptionPlanMapper: SubscriptionPlanMapper,
) : BaseViewModel<PaywallViewState, PaywallViewEvent, PaywallViewCommand>() {

    override val initState: PaywallViewState by lazyUnsafe { PaywallViewState.Loading }

    init {
        setDataViewState()
    }

    override fun onViewEventHappened(event: PaywallViewEvent) {
        when (event) {
            is PaywallViewEvent.OnBackIconClicked -> {
                viewModelScope.launch {
                    sendViewCommand(
                        PaywallViewCommand.NavigateBack
                    )
                }
            }
        }
    }

    private fun setDataViewState() {
        viewModelScope.launch {
            val paidFeatures = getPaidFeaturesUseCase().map { it.toUiModel() }
            val subscriptionPlans = getSubscriptionPlansUseCase()
            if (subscriptionPlans.isEmpty()) return@launch
            val planUiModels = subscriptionPlans.map {
                subscriptionPlanMapper.map(
                    plan = it,
                    allPlans = subscriptionPlans
                )
            }
            setViewState(
                PaywallViewState.Data(
                    paidFeatures = paidFeatures,
                    subscriptionPlans = planUiModels,
                    defaultSelectedPlanIndex = planUiModels
                        .indexOfFirst { it.savingPercents != null }
                        .takeIf { it != -1 }
                        ?: 0
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

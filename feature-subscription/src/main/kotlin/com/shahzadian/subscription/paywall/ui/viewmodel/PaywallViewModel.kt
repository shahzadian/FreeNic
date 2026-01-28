package com.shahzadian.subscription.paywall.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.activityevent.ActivityEvent
import com.freenic.activityevent.ActivityEventSender
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCase
import com.shahzadian.subscription.domain.GetSubscriptionPlansUseCase
import com.shahzadian.subscription.domain.PaidFeature
import com.shahzadian.subscription.domain.UserSubscriptionStatusChanged
import com.shahzadian.subscription.paywall.ui.PromoCodeHelper
import com.shahzadian.subscription.paywall.ui.SubscriptionPlanMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PaywallViewModel @Inject constructor(
    private val getPaidFeaturesUseCase: GetPaidFeaturesUseCase,
    private val getSubscriptionPlansUseCase: GetSubscriptionPlansUseCase,
    private val subscriptionPlanMapper: SubscriptionPlanMapper,
    private val analyticsEventSender: AnalyticsEventSender,
    private val activityEventSender: ActivityEventSender,
    private val promoCodeHelper: PromoCodeHelper,
) : BaseViewModel<PaywallViewState, PaywallViewEvent, PaywallViewCommand>() {

    override val initState: PaywallViewState by lazyUnsafe { PaywallViewState.Loading }

    init {
        setDataViewState()
        setupObservers()
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

            is PaywallViewEvent.OnUpgradeClicked -> {
                onUpgradeClicked(
                    planId = event.planId,
                )
            }

            is PaywallViewEvent.OnSubscriptionTermsClicked -> {
                onSubscriptionTermsClicked()
            }

            is PaywallViewEvent.OnPromoCodeEntered -> {
                onPromoCodeEntered(event.promoCode)
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
                    defaultSelectedPlanId = planUiModels
                        .firstOrNull { it.savingPercents != null }
                        ?.id
                        ?: planUiModels.first().id,
                    promoCodeState = if (promoCodeHelper.canEnterPromoCode()) {
                        PaywallViewState.PromoCodeState.Default
                    } else {
                        PaywallViewState.PromoCodeState.Blocked
                    },
                )
            )
        }
    }

    private fun setupObservers() {
        activityEventSender
            .resultFlow
            .onEach { event ->
                when (event) {
                    is UserSubscriptionStatusChanged -> {
                        sendViewCommand(
                            PaywallViewCommand.NavigateTo(
                                screen = AppScreen.DashboardScreen,
                                keepInStack = false,
                            )
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun PaidFeature.toUiModel(): PaywallViewState.PaidFeatureUiModel {
        return PaywallViewState.PaidFeatureUiModel(
            title = this.name,
            subtitle = this.description,
            icon = this.icon,
        )
    }

    private fun onPromoCodeEntered(promoCode: String) {
        val state = viewStateFlow.value as? PaywallViewState.Data ?: return
        viewModelScope.launch {
            setViewState(state.copy(promoCodeState = PaywallViewState.PromoCodeState.Loading))
            val isPromoCodeValid = promoCodeHelper.onPromoCodeEntered(promoCode)
            if (isPromoCodeValid) {
                setViewState(
                    state.copy(
                        promoCodeState = PaywallViewState.PromoCodeState.ValidationResult(
                            isValid = true,
                        )
                    )
                )
                delay(2000)
                sendViewCommand(
                    PaywallViewCommand.NavigateTo(
                        AppScreen.DashboardScreen
                    )
                )
            } else {
                if (promoCodeHelper.canEnterPromoCode()) {
                    setViewState(
                        state.copy(
                            promoCodeState = PaywallViewState.PromoCodeState.ValidationResult(
                                isValid = false,
                            )
                        )
                    )
                } else {
                    setViewState(
                        state.copy(
                            promoCodeState = PaywallViewState.PromoCodeState.Blocked
                        )
                    )
                }
            }
        }
    }

    private fun onUpgradeClicked(
        planId: String,
    ) {
        analyticsEventSender.send(
            AnalyticsEvent(
                name = AnalyticsEventConst.PAYWALL_UPGRADE_TAP,
                params = mapOf(
                    AnalyticsEventConst.PAYWALL_UPGRADE_TAP_PARAM_PLAN_ID to planId.asAnalyticsProperty(),
                )
            )
        )
        viewModelScope.launch {
            activityEventSender.sendEvent(
                ActivityEvent.AppSubscriptionRequest(
                    planId = planId,
                )
            )
        }
    }

    private fun onSubscriptionTermsClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.SUBSCRIPTION_TERMS_TAP
                )
            )
            activityEventSender.sendEvent(ActivityEvent.SubscriptionTermsOpenRequest)
        }
    }
}

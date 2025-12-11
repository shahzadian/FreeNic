package com.shahzadian.subscription.paywall.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import java.math.BigDecimal

@Immutable
internal sealed interface PaywallViewState : ViewState {

    @Immutable
    data object Loading : PaywallViewState

    @Immutable
    data class Data(
        val paidFeatures: List<PaidFeatureUiModel>,
        val subscriptionPlans: List<SubscriptionPlanUiModel>,
        val defaultSelectedPlanIndex: Int,
    ) : PaywallViewState

    @Immutable
    data class PaidFeatureUiModel(
        val title: TextWrapper,
        val subtitle: TextWrapper,
        val icon: ImageWrapper,
    )

    @Immutable
    data class SubscriptionPlanUiModel(
        val title: TextWrapper,
        val priceDetails: TextWrapper,
        val totalPrice: Float,
        val pricePerMonth: Float,
        val currency: String,
        val savingPercents: Int? = null,
    )
}

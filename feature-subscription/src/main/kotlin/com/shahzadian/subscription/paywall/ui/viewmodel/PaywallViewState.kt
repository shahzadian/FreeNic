package com.shahzadian.subscription.paywall.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface PaywallViewState : ViewState {

    @Immutable
    data object Loading : PaywallViewState

    @Immutable
    data class Data(
        val paidFeatures: List<PaidFeatureUiModel>,
        val subscriptionPlans: List<SubscriptionPlanUiModel>,
        val defaultSelectedPlanId: String,
        val promoCodeState: PromoCodeState,
    ) : PaywallViewState

    @Immutable
    data class PaidFeatureUiModel(
        val title: TextWrapper,
        val subtitle: TextWrapper,
        val icon: ImageWrapper,
    )

    @Immutable
    data class SubscriptionPlanUiModel(
        val id: String,
        val title: TextWrapper,
        val priceDetails: TextWrapper,
        val totalPrice: Float,
        val pricePerMonth: Float,
        val currency: String,
        val savingPercents: Int? = null,
    )

    @Immutable
    sealed interface PromoCodeState {

        @Immutable
        data object Default : PromoCodeState

        @Immutable
        data object Loading : PromoCodeState

        data object Blocked : PromoCodeState

        @Immutable
        data class ValidationResult(
            val isValid: Boolean,
        ) : PromoCodeState
    }
}

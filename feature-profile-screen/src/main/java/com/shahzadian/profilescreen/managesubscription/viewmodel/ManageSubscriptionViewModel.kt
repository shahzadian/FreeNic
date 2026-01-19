package com.shahzadian.profilescreen.managesubscription.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.ui.extensions.extractMainDisplayableDate
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.subscription.domain.CancelSubscriptionUseCase
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCase
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.SubscriptionPlan
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ManageSubscriptionViewModel @Inject constructor(
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val getPaidFeaturesUseCase: GetPaidFeaturesUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
    private val cancelSubscriptionUseCase: CancelSubscriptionUseCase,
) : BaseViewModel<ManageSubscriptionViewState, ManageSubscriptionViewEvent, ManageSubscriptionViewCommand>() {

    override val initState: ManageSubscriptionViewState by lazyUnsafe {
        ManageSubscriptionViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: ManageSubscriptionViewEvent) {
        when (event) {
            is ManageSubscriptionViewEvent.OnBackIconClicked -> {
                onBackIconClicked()
            }

            is ManageSubscriptionViewEvent.OnSubscriptionCancelClicked -> {
                onCancelSubscriptionClicked()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val currentSubscription =
                getUserCurrentSubscriptionUseCase() as? UserSubscriptionStatus.HasSubscription
                    ?: throw IllegalStateException("User has no subscription")
            setViewState(
                ManageSubscriptionViewState.Data(
                    currentPlanTitle = getSubscriptionTitle(
                        plan = currentSubscription.plan,
                    ),
                    activeUntil = extractMainDisplayableDate(currentSubscription.expiresAtMillis),
                    isAutoRenewing = currentSubscription.isAutoRenewing,
                    paidFeatures = getPaidFeaturesUseCase().map {
                        it.name
                    }
                )
            )
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(ManageSubscriptionViewCommand.NavigateBack)
        }
    }

    private fun onCancelSubscriptionClicked() {
        analyticsEventSender.send(
            AnalyticsEvent(
                name = AnalyticsEventConst.MANAGE_SUBSCRIPTION_CANCEL_TAP,
            )
        )
        viewModelScope.launch {
            val isCancelOpened = cancelSubscriptionUseCase()
            if (isCancelOpened) {
                sendViewCommand(ManageSubscriptionViewCommand.NavigateBack)
            }
        }
    }

    private fun getSubscriptionTitle(plan: SubscriptionPlan): TextWrapper {
        return when (plan.paymentFrequency) {
            SubscriptionPlan.PaymentFrequency.MONTHLY -> {
                TextWrapper.FromRes(R.string.subscription_plan_name_monthly)
            }

            SubscriptionPlan.PaymentFrequency.ANNUAL -> {
                TextWrapper.FromRes(R.string.subscription_plan_name_yearly)
            }
        }
    }
}
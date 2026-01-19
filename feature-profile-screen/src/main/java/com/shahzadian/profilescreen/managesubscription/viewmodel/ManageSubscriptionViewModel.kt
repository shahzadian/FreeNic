package com.shahzadian.profilescreen.managesubscription.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.extractMainDisplayableDate
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.SubscriptionPlan
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ManageSubscriptionViewModel @Inject constructor(
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
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
                    activeUntil = extractMainDisplayableDate(currentSubscription.expiresAtMillis)
                )
            )
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(ManageSubscriptionViewCommand.NavigateBack)
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
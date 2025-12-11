package com.shahzadian.subscription.paywall.ui

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.subscription.domain.SubscriptionPlan
import com.shahzadian.subscription.paywall.ui.viewmodel.PaywallViewState
import java.math.BigDecimal
import javax.inject.Inject

internal interface SubscriptionPlanMapper {

    fun map(
        plan: SubscriptionPlan,
        allPlans: List<SubscriptionPlan>
    ): PaywallViewState.SubscriptionPlanUiModel
}

internal class SubscriptionPlanMapperImpl @Inject constructor() : SubscriptionPlanMapper {

    override fun map(
        plan: SubscriptionPlan,
        allPlans: List<SubscriptionPlan>
    ): PaywallViewState.SubscriptionPlanUiModel {
        val title = getTitle(plan)
        return PaywallViewState.SubscriptionPlanUiModel(
            title = title,
            price = plan.priceForPeriod,
            currency = plan.currency.symbol,
            savingPercents = getSavingPercents(plan, allPlans),
        )
    }

    private fun getTitle(plan: SubscriptionPlan): TextWrapper {
        return when (plan.paymentFrequency) {
            SubscriptionPlan.PaymentFrequency.MONTHLY -> {
                TextWrapper.FromRes(R.string.subscription_plan_name_monthly)
            }

            SubscriptionPlan.PaymentFrequency.YEARLY -> {
                TextWrapper.FromRes(R.string.subscription_plan_name_yearly)
            }
        }
    }

    private fun getSavingPercents(
        plan: SubscriptionPlan,
        allPlans: List<SubscriptionPlan>
    ): Int? {
        var mostExpensivePlan: SubscriptionPlan? = null
        allPlans.forEach { item ->
            if (item.id == plan.id) return@forEach
            if (item.priceForPeriod > plan.priceForPeriod) {
                if (mostExpensivePlan == null || item.priceForPeriod > mostExpensivePlan.priceForPeriod) {
                    mostExpensivePlan = item
                }
            }
        }
        if (mostExpensivePlan == null) return null
        val saving =
            (plan.priceForPeriod / mostExpensivePlan.priceForPeriod) * BigDecimal.valueOf(100)
        return saving.intValueExact()
    }
}

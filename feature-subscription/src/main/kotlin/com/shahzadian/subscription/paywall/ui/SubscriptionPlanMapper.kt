package com.shahzadian.subscription.paywall.ui

import android.content.Context
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.subscription.domain.SubscriptionPlan
import com.shahzadian.subscription.domain.getPriceForYear
import com.shahzadian.subscription.paywall.ui.viewmodel.PaywallViewState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal interface SubscriptionPlanMapper {

    fun map(
        plan: SubscriptionPlan,
        allPlans: List<SubscriptionPlan>
    ): PaywallViewState.SubscriptionPlanUiModel
}

internal class SubscriptionPlanMapperImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : SubscriptionPlanMapper {

    override fun map(
        plan: SubscriptionPlan,
        allPlans: List<SubscriptionPlan>
    ): PaywallViewState.SubscriptionPlanUiModel {
        val title = getTitle(plan)
        return PaywallViewState.SubscriptionPlanUiModel(
            id = plan.id,
            title = title,
            priceDetails = getPriceDetails(plan),
            totalPrice = plan.totalPrice.toFloat(),
            pricePerMonth = plan.pricePerMonth.toFloat(),
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

    private fun getPriceDetails(plan: SubscriptionPlan): TextWrapper {
        return when (plan.paymentFrequency) {
            SubscriptionPlan.PaymentFrequency.MONTHLY -> {
                TextWrapper.FromRes(R.string.subscription_plan_price_per_month)
            }

            SubscriptionPlan.PaymentFrequency.YEARLY -> {
                val textFromRes = context.getString(
                    R.string.subscription_plan_price_per_month_postfix,
                    plan.pricePerMonth.toFloat()
                )
                TextWrapper.FromString("${plan.currency.symbol}$textFromRes")
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
            if (item.getPriceForYear() > plan.getPriceForYear()) {
                if (mostExpensivePlan == null || item.getPriceForYear() > mostExpensivePlan.getPriceForYear()) {
                    mostExpensivePlan = item
                }
            }
        }
        if (mostExpensivePlan == null) return null
        val percents =
            (plan.getPriceForYear().toFloat() / mostExpensivePlan.getPriceForYear().toFloat()) * 100
        return 100 - percents.toInt()
    }
}

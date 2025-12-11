package com.shahzadian.subscription.data

import com.shahzadian.subscription.domain.SubscriptionPlan
import java.math.BigDecimal
import java.util.Currency
import javax.inject.Inject

internal interface SubscriptionPlansRepository {

    suspend fun getPlans(): List<SubscriptionPlan>
}

internal class SubscriptionPlansRepositoryImpl @Inject constructor() : SubscriptionPlansRepository {

//    TODO
    override suspend fun getPlans(): List<SubscriptionPlan> {
        return listOf(
            SubscriptionPlan(
                id = ID_MONTHLY,
                paymentFrequency = SubscriptionPlan.PaymentFrequency.MONTHLY,
                priceForPeriod = BigDecimal.valueOf(4.99),
                currency = Currency.getInstance("USD")
            ),
            SubscriptionPlan(
                id = ID_YEARLY,
                paymentFrequency = SubscriptionPlan.PaymentFrequency.YEARLY,
                priceForPeriod = BigDecimal.valueOf(42),
                currency = Currency.getInstance("USD")
            )
        )
    }

    companion object {
        private const val ID_MONTHLY: String = "MONTHLY_PLAN"
        private const val ID_YEARLY: String = "YEARLY_PLAN"
    }
}

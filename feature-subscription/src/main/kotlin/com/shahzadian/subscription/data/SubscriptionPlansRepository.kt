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
                totalPrice = BigDecimal.valueOf(PRICE_MONTHLY_PER_MONTH),
                pricePerMonth = BigDecimal.valueOf(PRICE_MONTHLY_PER_MONTH),
                currency = Currency.getInstance("USD")
            ),
            SubscriptionPlan(
                id = ID_YEARLY,
                paymentFrequency = SubscriptionPlan.PaymentFrequency.YEARLY,
                totalPrice = BigDecimal.valueOf(PRICE_YEARLY_PER_MONTH * 12),
                pricePerMonth = BigDecimal.valueOf(PRICE_YEARLY_PER_MONTH),
                currency = Currency.getInstance("USD")
            )
        )
    }

    companion object {
        private const val ID_MONTHLY: String = "MONTHLY_PLAN"
        private const val ID_YEARLY: String = "YEARLY_PLAN"

        private const val PRICE_MONTHLY_PER_MONTH: Double = 5.0
        private const val PRICE_YEARLY_PER_MONTH: Double = 3.0
    }
}

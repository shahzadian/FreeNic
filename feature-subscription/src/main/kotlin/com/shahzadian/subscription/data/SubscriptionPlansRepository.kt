package com.shahzadian.subscription.data

import com.shahzadian.subscription.domain.SubscriptionPlan
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency
import javax.inject.Inject

internal interface SubscriptionPlansRepository {

    suspend fun getPlans(): List<SubscriptionPlan>
}

internal class SubscriptionPlansRepositoryImpl @Inject constructor() : SubscriptionPlansRepository {

    override suspend fun getPlans(): List<SubscriptionPlan> {
        return listOf(
            SubscriptionPlan(
                id = ID_WEEKLY,
                paymentFrequency = SubscriptionPlan.PaymentFrequency.WEEKLY,
                totalPrice = BigDecimal.valueOf(PRICE_MONTHLY_PER_WEEK),
                pricePerMonth = BigDecimal.valueOf(PRICE_MONTHLY_PER_WEEK)
                    .multiply(BigDecimal.valueOf(4)),
                currency = Currency.getInstance("USD")
            ),
            SubscriptionPlan(
                id = ID_MONTHLY,
                paymentFrequency = SubscriptionPlan.PaymentFrequency.MONTHLY,
                totalPrice = BigDecimal.valueOf(PRICE_MONTHLY_PER_MONTH),
                pricePerMonth = BigDecimal.valueOf(PRICE_MONTHLY_PER_MONTH),
                currency = Currency.getInstance("USD")
            ),
            SubscriptionPlan(
                id = ID_ANNUAL,
                paymentFrequency = SubscriptionPlan.PaymentFrequency.ANNUAL,
                totalPrice = BigDecimal.valueOf(PRICE_ANNUAL),
                pricePerMonth = BigDecimal.valueOf(PRICE_ANNUAL).divide(
                    BigDecimal.valueOf(12), 2,
                    RoundingMode.HALF_UP
                ),
                currency = Currency.getInstance("USD")
            )
        )
    }

    companion object {
        private const val ID_WEEKLY: String = "weekly_plan"
        private const val ID_MONTHLY: String = "monthly_plan"
        private const val ID_ANNUAL: String = "annual_plan"

        private const val PRICE_MONTHLY_PER_WEEK: Double = 1.5
        private const val PRICE_MONTHLY_PER_MONTH: Double = 5.0
        private const val PRICE_ANNUAL: Double = 50.0
    }
}

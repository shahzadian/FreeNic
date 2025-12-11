package com.shahzadian.subscription.domain

import java.math.BigDecimal
import java.util.Currency

class SubscriptionPlan(
    val id: String,
    val paymentFrequency: PaymentFrequency,
    val priceForPeriod: BigDecimal,
    val currency: Currency,
) {

    enum class PaymentFrequency {
        MONTHLY,
        YEARLY;
    }
}

fun SubscriptionPlan.getPriceForYear(): BigDecimal {
    return  when (this.paymentFrequency) {
        SubscriptionPlan.PaymentFrequency.MONTHLY -> this.priceForPeriod.multiply(BigDecimal(12))
        SubscriptionPlan.PaymentFrequency.YEARLY -> this.priceForPeriod
    }
}

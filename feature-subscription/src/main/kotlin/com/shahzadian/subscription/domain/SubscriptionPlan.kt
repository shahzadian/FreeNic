package com.shahzadian.subscription.domain

import java.math.BigDecimal
import java.util.Currency

class SubscriptionPlan(
    val id: String,
    val paymentFrequency: PaymentFrequency,
    val totalPrice: BigDecimal,
    val pricePerMonth: BigDecimal,
    val currency: Currency,
) {

    enum class PaymentFrequency {
        WEEKLY,
        MONTHLY,
        ANNUAL;
    }
}

fun SubscriptionPlan.getPriceForYear(): BigDecimal {
    return  when (this.paymentFrequency) {
        SubscriptionPlan.PaymentFrequency.WEEKLY -> this.totalPrice.multiply(BigDecimal(52))
        SubscriptionPlan.PaymentFrequency.MONTHLY -> this.totalPrice.multiply(BigDecimal(12))
        SubscriptionPlan.PaymentFrequency.ANNUAL -> this.totalPrice
    }
}

package com.shahzadian.subscription.domain

import com.shahzadian.subscription.subscription.SubscriptionRepository
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

internal interface UpdateSubscriptionUseCase {

    suspend operator fun invoke(plan: SubscriptionPlan)
}

internal class UpdateSubscriptionUseCaseImpl @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : UpdateSubscriptionUseCase {

    override suspend fun invoke(plan: SubscriptionPlan) {
        subscriptionRepository.onSubscriptionUpdated(
            planId = plan.id,
            expiresAtMillis = plan.getExpirationMillis(),
        )
    }

    private fun SubscriptionPlan.getExpirationMillis(): Long {
        val nowMillis = System.currentTimeMillis()
        return when (this.paymentFrequency) {
            SubscriptionPlan.PaymentFrequency.MONTHLY -> {
                Instant
                    .ofEpochMilli(nowMillis)
                    .atZone(ZoneId.systemDefault())
                    .plusMonths(1)
                    .toInstant()
                    .toEpochMilli()
            }

            SubscriptionPlan.PaymentFrequency.ANNUAL -> {
                Instant
                    .ofEpochMilli(nowMillis)
                    .atZone(ZoneId.systemDefault())
                    .plusYears(1)
                    .toInstant()
                    .toEpochMilli()
            }
        }
    }
}
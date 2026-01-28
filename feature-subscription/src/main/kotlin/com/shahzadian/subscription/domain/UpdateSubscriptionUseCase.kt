package com.shahzadian.subscription.domain

import com.shahzadian.subscription.subscription.SubscriptionRepository
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
        return when (this.paymentFrequency) {
            SubscriptionPlan.PaymentFrequency.MONTHLY -> {
                System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30
            }

            SubscriptionPlan.PaymentFrequency.ANNUAL -> {
                System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 365
            }
        }
    }
}
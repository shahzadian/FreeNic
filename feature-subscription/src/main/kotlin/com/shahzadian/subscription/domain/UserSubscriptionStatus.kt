package com.shahzadian.subscription.domain

sealed interface UserSubscriptionStatus {

    class HasSubscription(
        val plan: SubscriptionPlan,
        val expiresAtMillis: Long,
    ) : UserSubscriptionStatus

    object NoSubscription : UserSubscriptionStatus
}
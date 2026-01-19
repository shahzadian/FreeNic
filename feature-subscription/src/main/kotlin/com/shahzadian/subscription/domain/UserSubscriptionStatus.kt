package com.shahzadian.subscription.domain

sealed interface UserSubscriptionStatus {

    class HasSubscription(
        val plan: SubscriptionPlan,
        val expiresAtMillis: Long,
        val isAutoRenewing: Boolean,
    ) : UserSubscriptionStatus

    object NoSubscription : UserSubscriptionStatus
}
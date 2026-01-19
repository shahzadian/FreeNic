package com.shahzadian.subscription.domain

/**
 * Доменная модель статуса подписки пользователя.
 * Если экземпляр существует — у пользователя есть активная подписка на указанный план.
 * [expiresAtMillis] — ориентировочная дата окончания (epoch millis) или null,
 * если не удалось определить локально (для точности используйте серверную проверку/рефреш из Billing).
 */
data class UserSubscriptionStatus(
    val plan: SubscriptionPlan,
    val expiresAtMillis: Long?
)

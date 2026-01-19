package com.shahzadian.subscription.domain

import com.shahzadian.subscription.subscription.SubscriptionRepository
import javax.inject.Inject

/**
 * Юзкейс для обновления локального статуса подписки из Google Play Billing.
 * Рекомендуется вызывать при onStart приложения (offline-first: локально читаем, затем обновляем из SDK).
 */
interface RefreshSubscriptionStatusUseCase {
    suspend operator fun invoke(): SubscriptionPlan?
}

internal class RefreshSubscriptionStatusUseCaseImpl @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : RefreshSubscriptionStatusUseCase {

    override suspend fun invoke(): SubscriptionPlan? {
        // Триггерим обновление статуса через Billing-first (5с) внутри репозитория
        return subscriptionRepository.getCurrentSubscription()?.plan
    }
}

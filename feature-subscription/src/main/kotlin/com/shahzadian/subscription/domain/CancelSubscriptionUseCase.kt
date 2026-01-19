package com.shahzadian.subscription.domain

import com.shahzadian.subscription.subscription.SubscriptionRepository
import javax.inject.Inject
/**
 * Юзкейс открытия экрана управления подписками Google Play, где пользователь может отменить подписку.
 * Возвращает true, если получилось открыть экран управления подписками.
 */
interface CancelSubscriptionUseCase {
    suspend operator fun invoke(): Boolean
}

internal class CancelSubscriptionUseCaseImpl @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : CancelSubscriptionUseCase {

    override suspend fun invoke(): Boolean {
        // Делегируем открытие экрана подписок репозиторию
        return subscriptionRepository.cancelSubscription()
    }
}

package com.shahzadian.subscription.domain

import android.app.Activity
import com.shahzadian.subscription.subscription.BillingDataSource
import com.shahzadian.subscription.subscription.SubscriptionRepository
import javax.inject.Inject

/**
 * Юзкейс для запуска покупки подписки на премиум.
 * Возвращает true, если флоу биллинга был успешно запущен (не гарантирует успешную оплату).
 * После успешной оплаты рекомендуется вызвать RefreshSubscriptionStatusUseCase из BillingClient listener.
 */
interface SubscribeToPremiumUseCase {
    suspend operator fun invoke(activity: Activity, plan: SubscriptionPlan): Boolean
}

internal class SubscribeToPremiumUseCaseImpl @Inject constructor(
    private val billingDataSource: BillingDataSource,
    private val subscriptionRepository: SubscriptionRepository
) : SubscribeToPremiumUseCase {

    override suspend fun invoke(activity: Activity, plan: SubscriptionPlan): Boolean {
        // Сохраняем выбранный план локально как ожидаемый (опционально)
        subscriptionRepository.saveCurrentSubscription(plan.id)
        return billingDataSource.launchBillingFlow(activity, plan.id)
    }
}

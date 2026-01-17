package com.shahzadian.subscription.domain

import android.app.Activity
import com.shahzadian.subscription.subscription.BillingDataSource
import javax.inject.Inject

/**
 * Юзкейс для запуска покупки подписки на премиум.
 * Возвращает true, если флоу биллинга был успешно запущен (не гарантирует успешную оплату).
 * После успешной оплаты рекомендуется вызвать RefreshSubscriptionStatusUseCase из BillingClient listener.
 */
interface SubscribeToPremiumUseCase {
    suspend operator fun invoke(activity: Activity, planId: String): Boolean
}

internal class SubscribeToPremiumUseCaseImpl @Inject constructor(
    private val billingDataSource: BillingDataSource
) : SubscribeToPremiumUseCase {

    override suspend fun invoke(activity: Activity, planId: String): Boolean {
        return billingDataSource.launchBillingFlow(activity, planId)
    }
}

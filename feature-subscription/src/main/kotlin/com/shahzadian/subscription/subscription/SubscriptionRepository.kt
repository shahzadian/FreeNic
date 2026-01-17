package com.shahzadian.subscription.subscription

import com.shahzadian.subscription.data.SubscriptionPlansRepository
import com.shahzadian.subscription.domain.SubscriptionPlan
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

internal interface SubscriptionRepository {
    suspend fun getCurrentSubscription(): SubscriptionPlan?
    suspend fun saveCurrentSubscription(planId: String?)
    suspend fun refreshFromBilling(): SubscriptionPlan?
}

internal class SubscriptionRepositoryImpl @Inject constructor(
    private val local: SubscriptionLocalDataSource,
    private val plansRepository: SubscriptionPlansRepository,
    private val billing: BillingDataSource
) : SubscriptionRepository {

    // Единая синхронизация состояния репозитория: сериализуем чтения/записи локального кэша
    private val stateMutex = Mutex()

    override suspend fun getCurrentSubscription(): SubscriptionPlan? = stateMutex.withLock {
        val planId = local.getCurrentPlanId() ?: return@withLock null
        return@withLock plansRepository.getPlans().firstOrNull { it.id == planId }
    }

    override suspend fun saveCurrentSubscription(planId: String?) = stateMutex.withLock {
        local.setCurrentPlanId(planId)
    }

    override suspend fun refreshFromBilling(): SubscriptionPlan? {
        // Долгий I/O запрос в Billing выполняем вне критической секции
        val productId = billing.queryActiveSubscriptionProductId()
        return stateMutex.withLock {
            local.setCurrentPlanId(productId)
            if (productId == null) return@withLock null
            return@withLock plansRepository.getPlans().firstOrNull { it.id == productId }
        }
    }
}

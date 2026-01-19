package com.shahzadian.subscription.subscription

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.shahzadian.subscription.data.SubscriptionPlansRepository
import com.shahzadian.subscription.domain.SubscriptionPlan
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

internal interface SubscriptionRepository {
    /**
     * Обновляет локальный кэш состояния подписки (ид плана и ориентировочная дата окончания).
     * Можно вызывать из обработчиков Billing/Backend.
     */
    suspend fun onSubscriptionUpdated(planId: String?, expiresAtMillis: Long?)

    /**
     * Возвращает актуальный статус подписки пользователя:
     * - Сначала пытается получить данные из Google Play Billing (таймаут 5 секунд).
     * - Если не удалось (таймаут/ошибка/нет соединения) — отдаёт локально кэшированные данные.
     */
    suspend fun getCurrentSubscription(): UserSubscriptionStatus

    /** Открывает экран управления подписками в Google Play (для отмены/управления). */
    suspend fun cancelSubscription(): Boolean
}

internal class SubscriptionRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val local: SubscriptionLocalDataSource,
    private val plansRepository: SubscriptionPlansRepository,
    private val billing: BillingDataSource
) : SubscriptionRepository {

    // Единая синхронизация состояния репозитория: сериализуем чтения/записи локального кэша
    private val stateMutex = Mutex()

    override suspend fun onSubscriptionUpdated(planId: String?, expiresAtMillis: Long?) {
        stateMutex.withLock {
            local.setCurrentPlanId(planId)
            local.setCurrentExpiresAtMillis(expiresAtMillis)
        }
    }

    override suspend fun getCurrentSubscription(): UserSubscriptionStatus {
        // 1) Пытаемся получить из Billing с таймаутом 5 секунд (вне мьютекса)
        val active = withTimeoutOrNull(5_000) {
            billing.queryActiveSubscription()
        }
        if (active != null) {
            // Обновляем локальный кэш и возвращаем статус
            val status = stateMutex.withLock {
                local.setCurrentPlanId(active.productId)
                val expiresAt = estimateExpiryMillis(active.productId, active.purchaseTimeMillis)
                local.setCurrentExpiresAtMillis(expiresAt)
                val plan = plansRepository.getPlans().firstOrNull { it.id == active.productId }
                if (plan != null) UserSubscriptionStatus.HasSubscription(
                    plan = plan,
                    expiresAtMillis = expiresAt
                ) else null
            }
            if (status != null) return status
        }

        return stateMutex.withLock {
            val planId =
                local.getCurrentPlanId() ?: return@withLock UserSubscriptionStatus.NoSubscription
            val plan =
                plansRepository.getPlans().firstOrNull { it.id == planId }
                    ?: throw IllegalStateException("Unknown plan ID: $planId")
            val expiresAt = local.getCurrentExpiresAtMillis()
                ?: throw IllegalStateException("No expiresAtMillis for plan ID: $planId")
            UserSubscriptionStatus.HasSubscription(plan = plan, expiresAtMillis = expiresAt)
        }
    }

    override suspend fun cancelSubscription(): Boolean {
        // Берём локально известный план (чтобы подставить sku), иначе откроем общий список подписок
        val planId = stateMutex.withLock { local.getCurrentPlanId() }
        val packageName = appContext.packageName
        val uri = if (planId != null) {
            Uri.parse("https://play.google.com/store/account/subscriptions").buildUpon()
                .appendQueryParameter("sku", planId)
                .appendQueryParameter("package", packageName)
                .build()
        } else {
            Uri.parse("https://play.google.com/store/account/subscriptions")
        }

        val intent =
            Intent(Intent.ACTION_VIEW, uri).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        return try {
            appContext.startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }

    private suspend fun estimateExpiryMillis(productId: String, purchaseTimeMillis: Long): Long {
        val plan = plansRepository.getPlans().firstOrNull { it.id == productId }
            ?: throw IllegalArgumentException("Unknown plan ID: $productId")
        return when (plan.paymentFrequency) {
            SubscriptionPlan.PaymentFrequency.MONTHLY -> addMonthsMillis(purchaseTimeMillis, 1)
            SubscriptionPlan.PaymentFrequency.ANNUAL -> addYearsMillis(purchaseTimeMillis, 1)
        }
    }

    private fun addMonthsMillis(baseMillis: Long, months: Int): Long =
        java.util.Calendar.getInstance().apply {
            timeInMillis = baseMillis
            add(java.util.Calendar.MONTH, months)
        }.timeInMillis

    private fun addYearsMillis(baseMillis: Long, years: Int): Long =
        java.util.Calendar.getInstance().apply {
            timeInMillis = baseMillis
            add(java.util.Calendar.YEAR, years)
        }.timeInMillis
}

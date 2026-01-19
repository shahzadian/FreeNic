package com.shahzadian.subscription.domain

import com.freenic.ui.appinfo.AppInfoHelper
import com.shahzadian.debugparams.domain.DebugParamsHelper
import com.shahzadian.subscription.data.SubscriptionPlansRepository
import com.shahzadian.subscription.subscription.SubscriptionRepository
import javax.inject.Inject

/**
 * Получение информации о текущей подписке пользователя:
 * - есть ли активная подписка
 * - до какого числа она доступна (ориентировочно, если удалось вычислить локально)
 */
interface GetUserSubscriptionStatusUseCase {
    /**
     * @return null, если активной подписки нет
     */
    suspend operator fun invoke(): UserSubscriptionStatus?
}

internal class GetUserSubscriptionStatusUseCaseImpl @Inject constructor(
    private val debugParamsHelper: DebugParamsHelper,
    private val appInfoHelper: AppInfoHelper,
    private val subscriptionPlansRepository: SubscriptionPlansRepository,
    private val subscriptionRepository: SubscriptionRepository
) : GetUserSubscriptionStatusUseCase {

    override suspend fun invoke(): UserSubscriptionStatus? {
        // В отладочной сборке поддерживаем имитацию подписки
        if (appInfoHelper.isDebugBuild() && debugParamsHelper.getParams()?.hasPaidSubscription == true) {
            val plan = subscriptionPlansRepository.getPlans().firstOrNull() ?: return null
            return UserSubscriptionStatus(plan = plan, expiresAtMillis = null)
        }

        // Billing-first (5s) с фоллбэком на локальный кэш внутри репозитория
        return subscriptionRepository.getCurrentSubscription()
    }
}

package com.shahzadian.subscription.domain

import com.freenic.ui.appinfo.AppInfoHelper
import com.shahzadian.debugparams.domain.DebugParamsHelper
import com.shahzadian.subscription.data.SubscriptionPlansRepository
import com.shahzadian.subscription.subscription.SubscriptionRepository
import javax.inject.Inject

interface GetUserCurrentSubscriptionUseCase {

    /**
     * @return null if user is not subscribed
     */
    suspend operator fun invoke(): SubscriptionPlan?
}

internal class GetUserCurrentSubscriptionUseCaseImpl @Inject constructor(
    private val debugParamsHelper: DebugParamsHelper,
    private val appInfoHelper: AppInfoHelper,
    private val subscriptionPlansRepository: SubscriptionPlansRepository,
    private val subscriptionRepository: SubscriptionRepository
) : GetUserCurrentSubscriptionUseCase {

    override suspend fun invoke(): SubscriptionPlan? {
        // Debug override: emulate subscription if enabled in debug params
        if (appInfoHelper.isDebugBuild() && debugParamsHelper.getParams()?.hasPaidSubscription == true) {
            return subscriptionPlansRepository.getPlans().firstOrNull()
        }

        // Offline-first: read locally cached subscription (refreshed on app start by separate use case)
        return subscriptionRepository.getCurrentSubscription()
    }
}

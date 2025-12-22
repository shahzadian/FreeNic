package com.shahzadian.subscription.domain

import com.freenic.ui.appinfo.AppInfoHelper
import com.shahzadian.debugparams.domain.DebugParamsHelper
import com.shahzadian.subscription.data.SubscriptionPlansRepository
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
    private val subscriptionPlansRepository: SubscriptionPlansRepository
) : GetUserCurrentSubscriptionUseCase {

    override suspend fun invoke(): SubscriptionPlan? {
        return if (
            appInfoHelper.isDebugBuild() && debugParamsHelper.getParams()?.hasPaidSubscription == true
        ) {
            subscriptionPlansRepository.getPlans().firstOrNull()
        } else {
            null
        }
    }
}

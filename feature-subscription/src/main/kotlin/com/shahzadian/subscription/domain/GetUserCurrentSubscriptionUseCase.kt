package com.shahzadian.subscription.domain

import javax.inject.Inject

interface GetUserCurrentSubscriptionUseCase {

    /**
     * @return null if user is not subscribed
     */
    suspend operator fun invoke(): SubscriptionPlan?
}

internal class GetUserCurrentSubscriptionUseCaseImpl @Inject constructor(
) : GetUserCurrentSubscriptionUseCase {

    override suspend fun invoke(): SubscriptionPlan? {
//        TODO
        return null
    }
}

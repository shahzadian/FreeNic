package com.shahzadian.subscription.domain

import com.shahzadian.subscription.data.SubscriptionPlansRepository
import javax.inject.Inject

internal interface GetSubscriptionPlansUseCase {

    suspend operator fun invoke(): List<SubscriptionPlan>
}

internal class GetSubscriptionPlansUseCaseImpl @Inject constructor(
    private val subscriptionPlansRepository: SubscriptionPlansRepository
) : GetSubscriptionPlansUseCase {

    override suspend fun invoke(): List<SubscriptionPlan> {
        return subscriptionPlansRepository.getPlans()
    }
}

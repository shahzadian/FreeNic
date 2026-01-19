package com.shahzadian.subscription.domain

import com.shahzadian.subscription.data.PaidFeaturesRepository
import javax.inject.Inject

interface GetPaidFeaturesUseCase {

    suspend operator fun invoke() : List<PaidFeature>
}

internal class GetPaidFeaturesUseCaseImpl @Inject constructor(
    private val paidFeaturesRepository: PaidFeaturesRepository
) : GetPaidFeaturesUseCase {

    override suspend fun invoke(): List<PaidFeature> {
        return paidFeaturesRepository.getPaidFeatures()
    }
}

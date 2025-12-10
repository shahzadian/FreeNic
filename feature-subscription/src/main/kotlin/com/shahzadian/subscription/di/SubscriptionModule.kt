package com.shahzadian.subscription.di

import com.shahzadian.subscription.data.PaidFeaturesRepository
import com.shahzadian.subscription.data.PaidFeaturesRepositoryImpl
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCase
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SubscriptionModule {

    @Binds
    fun bindPaidFeaturesRepository(impl: PaidFeaturesRepositoryImpl): PaidFeaturesRepository

    @Binds
    fun bindGetPaidFeaturesUseCase(impl: GetPaidFeaturesUseCaseImpl): GetPaidFeaturesUseCase
}

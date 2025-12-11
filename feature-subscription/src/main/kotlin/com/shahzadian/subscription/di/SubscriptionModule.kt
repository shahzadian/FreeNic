package com.shahzadian.subscription.di

import com.shahzadian.subscription.data.PaidFeaturesRepository
import com.shahzadian.subscription.data.PaidFeaturesRepositoryImpl
import com.shahzadian.subscription.data.SubscriptionPlansRepository
import com.shahzadian.subscription.data.SubscriptionPlansRepositoryImpl
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCase
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCaseImpl
import com.shahzadian.subscription.domain.GetSubscriptionPlansUseCase
import com.shahzadian.subscription.domain.GetSubscriptionPlansUseCaseImpl
import com.shahzadian.subscription.paywall.ui.SubscriptionPlanMapper
import com.shahzadian.subscription.paywall.ui.SubscriptionPlanMapperImpl
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

    @Binds
    fun bindSubscriptionPlansRepository(impl: SubscriptionPlansRepositoryImpl): SubscriptionPlansRepository

    @Binds
    fun bindGetSubscriptionPlansUseCase(impl: GetSubscriptionPlansUseCaseImpl): GetSubscriptionPlansUseCase

    @Binds
    fun bindSubscriptionPlanMapper(impl: SubscriptionPlanMapperImpl): SubscriptionPlanMapper
}

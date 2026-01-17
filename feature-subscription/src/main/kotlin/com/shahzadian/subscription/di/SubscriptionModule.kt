package com.shahzadian.subscription.di

import com.shahzadian.subscription.data.PaidFeaturesRepository
import com.shahzadian.subscription.data.PaidFeaturesRepositoryImpl
import com.shahzadian.subscription.data.SubscriptionPlansRepository
import com.shahzadian.subscription.data.SubscriptionPlansRepositoryImpl
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCase
import com.shahzadian.subscription.domain.GetPaidFeaturesUseCaseImpl
import com.shahzadian.subscription.domain.GetSubscriptionPlansUseCase
import com.shahzadian.subscription.domain.GetSubscriptionPlansUseCaseImpl
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCaseImpl
import com.shahzadian.subscription.domain.RefreshSubscriptionStatusUseCase
import com.shahzadian.subscription.domain.RefreshSubscriptionStatusUseCaseImpl
import com.shahzadian.subscription.domain.SubscribeToPremiumUseCase
import com.shahzadian.subscription.domain.SubscribeToPremiumUseCaseImpl
import com.shahzadian.subscription.paywall.ui.SubscriptionPlanMapper
import com.shahzadian.subscription.paywall.ui.SubscriptionPlanMapperImpl
import com.shahzadian.subscription.subscription.BillingDataSource
import com.shahzadian.subscription.subscription.BillingDataSourceImpl
import com.shahzadian.subscription.subscription.SubscriptionLocalDataSource
import com.shahzadian.subscription.subscription.SubscriptionLocalDataSourceImpl
import com.shahzadian.subscription.subscription.SubscriptionRepository
import com.shahzadian.subscription.subscription.SubscriptionRepositoryImpl
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

    @Binds
    fun bindGetUserCurrentSubscriptionUseCase(
        impl: GetUserCurrentSubscriptionUseCaseImpl
    ): GetUserCurrentSubscriptionUseCase

    // Subscription offline-first infrastructure
    @Binds
    fun bindSubscriptionLocalDataSource(impl: SubscriptionLocalDataSourceImpl): SubscriptionLocalDataSource

    @Binds
    fun bindBillingDataSource(impl: BillingDataSourceImpl): BillingDataSource

    @Binds
    fun bindSubscriptionRepository(impl: SubscriptionRepositoryImpl): SubscriptionRepository

    // Use cases
    @Binds
    fun bindRefreshSubscriptionStatusUseCase(
        impl: RefreshSubscriptionStatusUseCaseImpl
    ): RefreshSubscriptionStatusUseCase

    @Binds
    fun bindSubscribeToPremiumUseCase(
        impl: SubscribeToPremiumUseCaseImpl
    ): SubscribeToPremiumUseCase
}

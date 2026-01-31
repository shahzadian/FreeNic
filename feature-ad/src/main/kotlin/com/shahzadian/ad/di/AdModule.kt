package com.shahzadian.ad.di

import com.shahzadian.ad.AdHelper
import com.shahzadian.ad.AdHelperImpl
import com.shahzadian.ad.reward.RewardHelperFactory
import com.shahzadian.ad.reward.RewardHelperFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AdModule {

    @Binds
    @Singleton
    fun bindAdHelperImpl(
        impl: AdHelperImpl
    ): AdHelper

    @Binds
    fun bindRewardHelperFactory(
        impl: RewardHelperFactoryImpl
    ): RewardHelperFactory
}

package com.freenic.di

import com.freenic.ads.AppAdsHelper
import com.freenic.ads.AppAdsHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CommonModule {

    @Binds
    fun bindAppAdsHelper(
        impl: AppAdsHelperImpl
    ): AppAdsHelper
}

package com.shahzadian.apprate.di

import com.shahzadian.apprate.domain.ShouldShowAppRateViewUseCase
import com.shahzadian.apprate.domain.ShouldShowAppRateViewUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AppRateModule {

    @Binds
    fun bindShouldShowAppRateViewUseCase(
        impl: ShouldShowAppRateViewUseCaseImpl
    ): ShouldShowAppRateViewUseCase
}

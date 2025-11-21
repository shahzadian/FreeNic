package com.shahzadian.dashboard.di

import com.shahzadian.dashboard.domain.GetUserDashboardUseCase
import com.shahzadian.dashboard.domain.GetUserDashboardUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DashboardModule {

    @Binds
    fun bindGetUserDashboardUseCase(impl: GetUserDashboardUseCaseImpl): GetUserDashboardUseCase
}
package com.shahzadian.homescreen.di

import com.shahzadian.homescreen.ui.welcomescreen.data.OnboardingDataRepository
import com.shahzadian.homescreen.ui.welcomescreen.data.OnboardingDataRepositoryImpl
import com.shahzadian.homescreen.ui.welcomescreen.domain.GetOnboardingDataUseCase
import com.shahzadian.homescreen.ui.welcomescreen.domain.GetOnboardingDataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface HomeScreenModule {

    @Binds
    fun bindOnboardingDataRepository(impl: OnboardingDataRepositoryImpl): OnboardingDataRepository

    @Binds
    fun bindGetOnboardingDataUseCase(impl: GetOnboardingDataUseCaseImpl): GetOnboardingDataUseCase
}
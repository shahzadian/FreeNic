package com.shahzadian.distractionactivityscreen.di

import com.shahzadian.distractionactivityscreen.domain.GetDistractionActivitiesUseCase
import com.shahzadian.distractionactivityscreen.domain.GetDistractionActivitiesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DistractionActivityModule {

    @Binds
    fun bindGetDistractionActivityUseCase(impl: GetDistractionActivitiesUseCaseImpl) : GetDistractionActivitiesUseCase
}

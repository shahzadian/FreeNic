package com.shahzadian.cravings.di

import com.shahzadian.cravings.data.CravingToolsRepository
import com.shahzadian.cravings.data.CravingToolsRepositoryImpl
import com.shahzadian.cravings.domain.GetCravingToolsUseCase
import com.shahzadian.cravings.domain.GetCravingToolsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CravingsModule {

    @Binds
    fun bindGetCravingsUseCase(impl: GetCravingToolsUseCaseImpl): GetCravingToolsUseCase

    @Binds
    fun bindCravingsRepository(impl: CravingToolsRepositoryImpl): CravingToolsRepository
}

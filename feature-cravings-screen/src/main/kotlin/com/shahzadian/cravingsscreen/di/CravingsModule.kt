package com.shahzadian.cravingsscreen.di

import com.shahzadian.cravingsscreen.data.CravingToolsRepository
import com.shahzadian.cravingsscreen.data.CravingToolsRepositoryImpl
import com.shahzadian.cravingsscreen.domain.GetCravingToolsUseCase
import com.shahzadian.cravingsscreen.domain.GetCravingToolsUseCaseImpl
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

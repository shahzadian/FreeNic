package com.shahzadian.mainscreen.di

import com.shahzadian.mainscreen.data.MainScreenPageRepository
import com.shahzadian.mainscreen.data.MainScreenPageRepositoryImpl
import com.shahzadian.mainscreen.domain.GetMainScreenPagesUseCase
import com.shahzadian.mainscreen.domain.GetMainScreenPagesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MainScreenModule {

    @Binds
    fun bindMainScreenPageRepository(
        impl: MainScreenPageRepositoryImpl
    ): MainScreenPageRepository

    @Binds
    fun bindGetMainScreenPagesUseCase(
        impl: GetMainScreenPagesUseCaseImpl
    ): GetMainScreenPagesUseCase
}

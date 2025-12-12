package com.shahzadian.achievements.di

import com.shahzadian.achievements.data.AchievementRepository
import com.shahzadian.achievements.data.AchievementRepositoryImpl
import com.shahzadian.achievements.domain.AchievementStateProviderFactory
import com.shahzadian.achievements.domain.AchievementStateProviderFactoryImpl
import com.shahzadian.achievements.domain.GetAchievementsUseCase
import com.shahzadian.achievements.domain.GetAchievementsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AchievementsModule {

    @Binds
    fun bindAchievementsStateProviderFactory(
        impl: AchievementStateProviderFactoryImpl
    ): AchievementStateProviderFactory

    @Binds
    fun bindAchievementRepository(
        impl: AchievementRepositoryImpl
    ): AchievementRepository

    @Binds
    fun bindGetAchievementsUseCase(
        impl: GetAchievementsUseCaseImpl
    ): GetAchievementsUseCase
}

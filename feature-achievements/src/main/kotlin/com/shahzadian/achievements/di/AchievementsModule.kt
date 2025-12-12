package com.shahzadian.achievements.di

import com.shahzadian.achievements.domain.AchievementStateProviderFactory
import com.shahzadian.achievements.domain.AchievementStateProviderFactoryImpl
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
}

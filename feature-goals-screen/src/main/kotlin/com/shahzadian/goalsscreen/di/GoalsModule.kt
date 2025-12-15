package com.shahzadian.goalsscreen.di

import com.shahzadian.goalsscreen.ui.AchievementMapper
import com.shahzadian.goalsscreen.ui.AchievementMapperImpl
import com.shahzadian.goalsscreen.ui.AchievementsSorter
import com.shahzadian.goalsscreen.ui.AchievementsSorterImpl
import com.shahzadian.goalsscreen.ui.AchievementsUiModelFetcher
import com.shahzadian.goalsscreen.ui.AchievementsUiModelFetcherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface GoalsModule {

    @Binds
    fun bindAchievementMapper(
        impl: AchievementMapperImpl
    ): AchievementMapper

    @Binds
    fun bindAchievementsSorter(
        impl: AchievementsSorterImpl
    ): AchievementsSorter

    @Binds
    @Singleton
    fun bindAchievementsUiModelFetcher(
        impl: AchievementsUiModelFetcherImpl
    ): AchievementsUiModelFetcher
}

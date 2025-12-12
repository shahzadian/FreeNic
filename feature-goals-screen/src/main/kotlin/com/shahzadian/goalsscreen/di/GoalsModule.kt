package com.shahzadian.goalsscreen.di

import com.shahzadian.goalsscreen.ui.AchievementMapper
import com.shahzadian.goalsscreen.ui.AchievementMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface GoalsModule {

    @Binds
    fun bindAchievementMapper(
        impl: AchievementMapperImpl
    ): AchievementMapper
}

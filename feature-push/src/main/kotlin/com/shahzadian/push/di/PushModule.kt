package com.shahzadian.push.di

import com.shahzadian.push.api.NotificationChecker
import com.shahzadian.push.checkers.achievements.AchievementsNotificationChecker
import com.shahzadian.push.dispatcher.PushDispatcher
import com.shahzadian.push.dispatcher.PushDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PushBindModule {

    @Binds
    @Singleton
    abstract fun bindPushDispatcher(impl: PushDispatcherImpl): PushDispatcher

    @Binds
    @IntoSet
    abstract fun bindAchievementsChecker(impl: AchievementsNotificationChecker): NotificationChecker
}

@Module
@InstallIn(SingletonComponent::class)
object PushProvideModule {


}

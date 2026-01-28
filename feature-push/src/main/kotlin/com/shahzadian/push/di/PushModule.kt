package com.shahzadian.push.di

import com.shahzadian.push.api.NotificationChecker
import com.shahzadian.push.checkers.EveningNotificationChecker
import com.shahzadian.push.checkers.MorningNotificationChecker
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
    abstract fun bindAchievementsChecker(impl: MorningNotificationChecker): NotificationChecker

    @Binds
    @IntoSet
    @Evening
    abstract fun bindEveningSimpleReminderChecker(impl: EveningNotificationChecker): NotificationChecker
}

@Module
@InstallIn(SingletonComponent::class)
object PushProvideModule {


}

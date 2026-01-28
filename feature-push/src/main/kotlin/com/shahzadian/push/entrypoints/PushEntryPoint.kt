package com.shahzadian.push.entrypoints

import com.shahzadian.push.api.NotificationChecker
import com.shahzadian.push.di.Evening
import com.shahzadian.push.dispatcher.PushDispatcher
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * EntryPoint для доступа к зависимостям из Worker без использования HiltWorkerFactory.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface PushEntryPoint {
    fun pushDispatcher(): PushDispatcher
    fun notificationCheckers(): Set<@JvmSuppressWildcards NotificationChecker>
    @Evening
    fun eveningNotificationCheckers(): Set<@JvmSuppressWildcards NotificationChecker>
}

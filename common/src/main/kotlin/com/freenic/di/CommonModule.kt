package com.freenic.di

import com.freenic.activityevent.ActivityEventHelper
import com.freenic.activityevent.ActivityEventReceiver
import com.freenic.activityevent.ActivityEventSender
import com.freenic.ui.model.CoroutineContexts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface CommonModule {

    companion object {

        @Provides
        @Singleton
        fun provideCoroutineContexts(): CoroutineContexts {
            return CoroutineContexts(
                main = Dispatchers.Main,
                io = Dispatchers.IO,
                default = Dispatchers.Default,
                unconfined = Dispatchers.Unconfined,
            )
        }

        @Provides
        @Singleton
        fun provideActivityEventSender(
            helper: ActivityEventHelper
        ): ActivityEventSender {
            return helper
        }

        @Provides
        @Singleton
        fun provideActivityEventReceiver(
            helper: ActivityEventHelper
        ): ActivityEventReceiver {
            return helper
        }
    }
}

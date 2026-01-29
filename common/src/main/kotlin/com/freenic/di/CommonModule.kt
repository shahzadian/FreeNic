package com.freenic.di

import android.content.Context
import android.content.SharedPreferences
import com.freenic.CommonConst
import com.freenic.activityevent.ActivityEventHelper
import com.freenic.activityevent.ActivityEventReceiver
import com.freenic.activityevent.ActivityEventSender
import com.freenic.cache.AppCache
import com.freenic.cache.AppCacheImpl
import com.freenic.ui.model.CoroutineContexts
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface CommonModule {

    @Binds
    fun bindAppCache(
        impl: AppCacheImpl
    ): AppCache

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

        @Provides
        @Singleton
        @Named(CommonConst.PREF_NAME_APP_SETTINGS)
        fun providePrefs(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(
                CommonConst.PREF_NAME_APP_SETTINGS,
                Context.MODE_PRIVATE
            )
        }
    }
}

package com.shahzadian.freenic.di

import android.content.Context
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.domain.ClearAppDataUseCase
import com.freenic.logger.AppLogger
import com.freenic.ui.appinfo.AppInfoHelper
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.freenic.analytics.AnalyticsEventSenderDebug
import com.shahzadian.freenic.analytics.AnalyticsEventSenderProd
import com.shahzadian.freenic.appinfohelper.AppInfoHelperImpl
import com.shahzadian.freenic.domain.clearappdata.ClearAppDataUseCaseImpl
import com.shahzadian.freenic.domain.clearappdata.appclearabledata.AppClearableDataFactory
import com.shahzadian.freenic.domain.clearappdata.appclearabledata.AppClearableDataFactoryImpl
import com.shahzadian.freenic.logger.AppLoggerImpl
import com.shahzadian.freenic.navigation.AppNavigatorFactory
import com.shahzadian.freenic.navigation.AppNavigatorFactoryImpl
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AppModule {

    @Binds
    fun bindAppInfoHelper(impl: AppInfoHelperImpl): AppInfoHelper

    @Binds
    fun bindAppClearableDataFactory(impl: AppClearableDataFactoryImpl): AppClearableDataFactory

    @Binds
    fun bindClearAppDataUseCase(impl: ClearAppDataUseCaseImpl): ClearAppDataUseCase

    @Binds
    fun bindAppNavigatorFactory(impl: AppNavigatorFactoryImpl): AppNavigatorFactory

    @Binds
    fun bindAppLogger(impl: AppLoggerImpl): AppLogger

    companion object {

        @Provides
        @Singleton
        fun provideAnalyticsEventSender(
            appInfoHelper: AppInfoHelper,
            appLogger: AppLogger,
            getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
            coroutineContexts: CoroutineContexts,
            @ApplicationContext
            context: Context,
        ): AnalyticsEventSender {
            return if (appInfoHelper.isDebugBuild()) {
                AnalyticsEventSenderDebug(
                    appLogger = appLogger,
                    getUserCurrentSubscriptionUseCase = getUserCurrentSubscriptionUseCase,
                    appInfoHelper = appInfoHelper,
                    coroutineContexts = coroutineContexts,
                )
            } else {
                AnalyticsEventSenderProd(
                    context = context,
                    getUserCurrentSubscriptionUseCase = getUserCurrentSubscriptionUseCase,
                    appInfoHelper = appInfoHelper,
                    coroutineContexts = coroutineContexts,
                )
            }
        }
    }
}

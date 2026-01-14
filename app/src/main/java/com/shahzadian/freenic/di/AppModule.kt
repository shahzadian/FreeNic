package com.shahzadian.freenic.di

import com.freenic.analytics.AnalyticsEventSender
import com.freenic.domain.ClearAppDataUseCase
import com.freenic.ui.appinfo.AppInfoHelper
import com.shahzadian.freenic.analytics.AnalyticsEventSenderImpl
import com.shahzadian.freenic.appinfohelper.AppInfoHelperImpl
import com.shahzadian.freenic.domain.clearappdata.ClearAppDataUseCaseImpl
import com.shahzadian.freenic.domain.clearappdata.appclearabledata.AppClearableDataFactory
import com.shahzadian.freenic.domain.clearappdata.appclearabledata.AppClearableDataFactoryImpl
import com.shahzadian.freenic.navigation.AppNavigatorFactory
import com.shahzadian.freenic.navigation.AppNavigatorFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
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
    @Singleton
    fun bindAnalyticsEventSender(impl: AnalyticsEventSenderImpl): AnalyticsEventSender

    @Binds
    fun bindAppNavigatorFactory(impl: AppNavigatorFactoryImpl): AppNavigatorFactory
}

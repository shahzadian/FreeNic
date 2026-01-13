package com.shahzadian.freenic.di

import com.freenic.domain.ClearAppDataUseCase
import com.freenic.ui.appinfo.AppInfoHelper
import com.shahzadian.freenic.appinfohelper.AppInfoHelperImpl
import com.shahzadian.freenic.domain.clearappdata.ClearAppDataUseCaseImpl
import com.shahzadian.freenic.domain.clearappdata.appclearabledata.AppClearableDataFactory
import com.shahzadian.freenic.domain.clearappdata.appclearabledata.AppClearableDataFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AppModule {

    @Binds
    fun bindAppInfoHelper(impl: AppInfoHelperImpl): AppInfoHelper

    @Binds
    fun bindAppClearableDataFactory(impl: AppClearableDataFactoryImpl): AppClearableDataFactory

    @Binds
    fun bindClearAppDataUseCase(impl: ClearAppDataUseCaseImpl): ClearAppDataUseCase
}

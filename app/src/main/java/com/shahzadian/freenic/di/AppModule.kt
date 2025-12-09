package com.shahzadian.freenic.di

import com.freenic.ui.appinfo.AppInfoHelper
import com.shahzadian.freenic.appinfohelper.AppInfoHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AppModule {

    @Binds
    fun bindAppInfoHelper(impl: AppInfoHelperImpl): AppInfoHelper
}

package com.shahzadian.datafill.di

import com.shahzadian.datafill.domain.FillableInfoHelper
import com.shahzadian.datafill.domain.FillableInfoHelperImpl
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProviderFactory
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProviderFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataFillModule {

    @Binds
    fun bindFillableInfoProviderFactory(impl: FillableInfoProviderFactoryImpl): FillableInfoProviderFactory

    @Binds
    fun bindFillableInfoHelper(impl: FillableInfoHelperImpl) : FillableInfoHelper
}

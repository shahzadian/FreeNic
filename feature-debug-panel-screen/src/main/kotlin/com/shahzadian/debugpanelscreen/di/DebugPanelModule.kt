package com.shahzadian.debugpanelscreen.di

import com.shahzadian.debugpanelscreen.data.DebugPanelParamsRepository
import com.shahzadian.debugpanelscreen.data.DebugPanelParamsRepositoryImpl
import com.shahzadian.debugpanelscreen.data.datastore.DebugDataStoreProvider
import com.shahzadian.debugpanelscreen.data.datastore.DebugDataStoreProviderImpl
import com.shahzadian.debugpanelscreen.domain.DebugPanelParamsHelper
import com.shahzadian.debugpanelscreen.domain.DebugPanelParamsHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DebugPanelModule {

    @Binds
    fun bindDebugPanelParamsRepository(
        impl: DebugPanelParamsRepositoryImpl
    ): DebugPanelParamsRepository

    @Binds
    fun bindDebugPanelParamsHelper(
        impl: DebugPanelParamsHelperImpl
    ): DebugPanelParamsHelper

    @Binds
    @Singleton
    fun bindDebugDataStoreProvider(
        impl: DebugDataStoreProviderImpl
    ): DebugDataStoreProvider
}

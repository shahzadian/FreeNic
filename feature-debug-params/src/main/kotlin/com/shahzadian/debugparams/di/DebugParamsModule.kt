package com.shahzadian.debugparams.di

import com.shahzadian.debugparams.data.DebugParamsRepository
import com.shahzadian.debugparams.data.DebugParamsRepositoryImpl
import com.shahzadian.debugparams.data.datastore.DebugParamsDataStoreProvider
import com.shahzadian.debugparams.data.datastore.DebugParamsDataStoreProviderImpl
import com.shahzadian.debugparams.domain.DebugParamsHelper
import com.shahzadian.debugparams.domain.DebugParamsHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DebugParamsModule {

    @Binds
    @Singleton
    fun bindDebugParamsDataStoreProvider(
        impl: DebugParamsDataStoreProviderImpl
    ): DebugParamsDataStoreProvider

    @Binds
    fun bindDebugParamsRepository(impl: DebugParamsRepositoryImpl): DebugParamsRepository

    @Binds
    fun bindDebugParamsHelper(impl: DebugParamsHelperImpl): DebugParamsHelper
}

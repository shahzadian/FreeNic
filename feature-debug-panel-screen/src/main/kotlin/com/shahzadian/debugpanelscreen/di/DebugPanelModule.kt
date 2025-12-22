package com.shahzadian.debugpanelscreen.di

import com.shahzadian.debugpanelscreen.domain.DebugPanelParamsHelper
import com.shahzadian.debugpanelscreen.domain.DebugPanelParamsHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DebugPanelModule {

    @Binds
    fun bindDebugPanelParamsHelper(
        impl: DebugPanelParamsHelperImpl
    ): DebugPanelParamsHelper
}

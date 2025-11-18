package com.freenic.ui.di

import com.freenic.ui.model.CoroutineContexts
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal interface CommonModule {

    fun providerCoroutineContexts(): CoroutineContexts {
        return CoroutineContexts(
            main = Dispatchers.Main,
            io = Dispatchers.IO,
            default = Dispatchers.Default,
            unconfined = Dispatchers.Unconfined,
        )
    }
}
package com.freenic.ui.di

import com.freenic.ui.model.CoroutineContexts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CommonModule {

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
}

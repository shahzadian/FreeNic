package com.shahzadian.slipscreen.ui.di

import com.shahzadian.slipscreen.ui.slipcontext.data.SlipContextRepository
import com.shahzadian.slipscreen.ui.slipcontext.data.SlipContextRepositoryImpl
import com.shahzadian.slipscreen.ui.slipcontext.domain.GetSlipContextsUseCase
import com.shahzadian.slipscreen.ui.slipcontext.domain.GetSlipContextsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SlipModule {

    @Binds
    fun bindSlipContextRepository(
        impl: SlipContextRepositoryImpl
    ): SlipContextRepository

    @Binds
    fun bindGetSlipContextsUseCase(
        impl: GetSlipContextsUseCaseImpl
    ): GetSlipContextsUseCase
}

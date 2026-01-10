package com.shahzadian.slipscreen.ui.di

import com.shahzadian.slipscreen.ui.slipreason.data.SlipReasonRepository
import com.shahzadian.slipscreen.ui.slipreason.data.SlipReasonRepositoryImpl
import com.shahzadian.slipscreen.ui.slipreason.domain.GetSlipReasonsUseCase
import com.shahzadian.slipscreen.ui.slipreason.domain.GetSlipReasonsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SlipModule {

    @Binds
    fun bindSlipContextRepository(
        impl: SlipReasonRepositoryImpl
    ): SlipReasonRepository

    @Binds
    fun bindGetSlipContextsUseCase(
        impl: GetSlipReasonsUseCaseImpl
    ): GetSlipReasonsUseCase
}

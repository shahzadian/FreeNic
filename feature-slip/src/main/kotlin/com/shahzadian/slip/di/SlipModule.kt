package com.shahzadian.slip.di

import com.shahzadian.slip.slipreason.data.SlipReasonRepository
import com.shahzadian.slip.slipreason.data.SlipReasonRepositoryImpl
import com.shahzadian.slip.slipreason.domain.GetSlipReasonsUseCase
import com.shahzadian.slip.slipreason.domain.GetSlipReasonsUseCaseImpl
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

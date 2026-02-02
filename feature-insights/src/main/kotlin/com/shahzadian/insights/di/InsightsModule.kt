package com.shahzadian.insights.di

import com.shahzadian.insights.domain.GetInsightChartsBlocksUseCase
import com.shahzadian.insights.domain.GetInsightChartsBlocksUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface InsightsModule {

    @Binds
    fun bindGetInsightsUseCase(
        impl: GetInsightChartsBlocksUseCaseImpl
    ): GetInsightChartsBlocksUseCase
}

package com.shahzadian.insightsscreen.di

import com.shahzadian.insightsscreen.domain.GetInsightChartsBlocksUseCase
import com.shahzadian.insightsscreen.domain.GetInsightChartsBlocksUseCaseImpl
import com.shahzadian.insightsscreen.ui.InsightChartMapper
import com.shahzadian.insightsscreen.ui.InsightChartMapperImpl
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

    @Binds
    fun bindInsightChartMapper(
        impl: InsightChartMapperImpl
    ): InsightChartMapper
}

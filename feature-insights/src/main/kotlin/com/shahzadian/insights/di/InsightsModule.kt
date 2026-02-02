package com.shahzadian.insights.di

import com.shahzadian.insights.data.InsightsChartsRepositoryFactory
import com.shahzadian.insights.data.InsightsChartsRepositoryFactoryImpl
import com.shahzadian.insights.data.SlipCaseChartHelper
import com.shahzadian.insights.data.SlipCaseChartHelperImpl
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

    @Binds
    fun bindInsightsChartsRepositoryFactory(
        impl: InsightsChartsRepositoryFactoryImpl
    ): InsightsChartsRepositoryFactory

    @Binds
    fun bindSlipChaseChartHelper(
        impl: SlipCaseChartHelperImpl
    ): SlipCaseChartHelper
}

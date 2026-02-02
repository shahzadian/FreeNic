package com.shahzadian.insights.domain

import com.shahzadian.insights.data.InsightsChartsRepositoryFactory
import javax.inject.Inject

interface GetInsightChartsBlocksUseCase {

    suspend operator fun invoke(
        params: InsightChart.Params = InsightChart.Params.default()
    ): List<InsightChartsBlock>
}

internal class GetInsightChartsBlocksUseCaseImpl @Inject constructor(
    private val insightsChartsRepositoryFactory: InsightsChartsRepositoryFactory,
) : GetInsightChartsBlocksUseCase {

    override suspend fun invoke(
        params: InsightChart.Params,
    ): List<InsightChartsBlock> {
        val repository = insightsChartsRepositoryFactory.create(
            params = params,
        )
        return repository.getAllBlocks()
    }
}
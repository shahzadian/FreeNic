package com.shahzadian.insights.domain

import com.shahzadian.insights.data.InsightChartsRepository
import com.shahzadian.recoverycalculator.domain.factory.HealthRecoveryCalculatorFactory
import javax.inject.Inject

interface GetInsightChartsBlocksUseCase {

    suspend operator fun invoke(): List<InsightChartsBlock>
}

internal class GetInsightChartsBlocksUseCaseImpl @Inject constructor(
    private val recoveryCalculatorFactory: HealthRecoveryCalculatorFactory,
) : GetInsightChartsBlocksUseCase {

    override suspend fun invoke(): List<InsightChartsBlock> {
        val recoveryCalculator = recoveryCalculatorFactory.create()
        val repository = InsightChartsRepository(recoveryCalculator)
        return repository.getAllBlocks()
    }
}
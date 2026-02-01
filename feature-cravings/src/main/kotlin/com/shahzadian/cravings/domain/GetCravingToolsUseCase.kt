package com.shahzadian.cravings.domain

import com.shahzadian.cravings.data.CravingToolsRepository
import javax.inject.Inject

interface GetCravingToolsUseCase {

    suspend operator fun invoke(): List<CravingTool>
}

internal class GetCravingToolsUseCaseImpl @Inject constructor(
    private val cravingToolsRepository: CravingToolsRepository
) : GetCravingToolsUseCase {

    override suspend fun invoke(): List<CravingTool> {
        return cravingToolsRepository.getCravingTools()
    }
}

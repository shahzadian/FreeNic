package com.shahzadian.cravingsscreen.domain

import com.shahzadian.cravingsscreen.data.CravingToolsRepository
import javax.inject.Inject

internal interface GetCravingToolsUseCase {

    suspend operator fun invoke(): List<CravingTool>
}

internal class GetCravingToolsUseCaseImpl @Inject constructor(
    private val cravingToolsRepository: CravingToolsRepository
) : GetCravingToolsUseCase {

    override suspend fun invoke(): List<CravingTool> {
        return cravingToolsRepository.getCravingTools()
    }
}

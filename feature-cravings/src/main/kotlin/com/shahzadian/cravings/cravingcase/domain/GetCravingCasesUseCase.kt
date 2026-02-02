package com.shahzadian.cravings.cravingcase.domain

import com.shahzadian.cravings.cravingcase.data.CravingCaseRepository
import javax.inject.Inject

interface GetCravingCasesUseCase {

    suspend operator fun invoke(): List<CravingCase>
}

internal class GetCravingCasesUseCaseImpl @Inject constructor(
    private val cravingCaseRepository: CravingCaseRepository,
) : GetCravingCasesUseCase {

    override suspend fun invoke(): List<CravingCase> {
        return cravingCaseRepository.getAll()
    }
}

interface AddCravingCaseUseCase {

    suspend operator fun invoke(cravingCase: CravingCase)
}

internal class AddCravingCaseUseCaseImpl @Inject constructor(
    private val cravingCaseRepository: CravingCaseRepository,
) : AddCravingCaseUseCase {

    override suspend fun invoke(cravingCase: CravingCase) {
        val shouldFix = cravingCaseRepository
            .getLatestCravingCase()
            ?.let { case ->
                System.currentTimeMillis() - case.caseMillis > 1000 * 60 * 60
            } ?: true
        if (shouldFix) {
            cravingCaseRepository.insert(cravingCase)
        }
    }
}

interface ClearCravingCasesUseCase {

    suspend operator fun invoke()
}

internal class ClearCravingCasesUseCaseImpl @Inject constructor(
    private val cravingCaseRepository: CravingCaseRepository,
) : ClearCravingCasesUseCase {

    override suspend fun invoke() {
        cravingCaseRepository.clear()
    }
}
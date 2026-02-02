package com.shahzadian.cravings.cravingcase.data

import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.cravings.cravingcase.db.CravingCaseDatabase
import com.shahzadian.cravings.cravingcase.db.CravingCaseEntity
import com.shahzadian.cravings.cravingcase.domain.CravingCase
import com.shahzadian.cravings.cravingcase.domain.CravingStrength
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface CravingCaseRepository {

    suspend fun getLatestCravingCase(): CravingCase?

    suspend fun insert(cravingCase: CravingCase)

    suspend fun getAll(): List<CravingCase>

    suspend fun clear()
}

internal class CravingCaseRepositoryImpl @Inject constructor(
    private val cravingCaseDatabase: CravingCaseDatabase,
    private val coroutineContexts: CoroutineContexts,
) : CravingCaseRepository {

    override suspend fun getLatestCravingCase(): CravingCase? {
        return withContext(coroutineContexts.io) {
            cravingCaseDatabase.cravingCaseDao().getLatestCravingCase()?.toDomain()
        }
    }

    override suspend fun insert(cravingCase: CravingCase) {
        withContext(coroutineContexts.io) {
            cravingCaseDatabase.cravingCaseDao().insert(cravingCase.toEntity())
        }
    }

    override suspend fun getAll(): List<CravingCase> {
        return withContext(coroutineContexts.io) {
            cravingCaseDatabase.cravingCaseDao().getAll().map { it.toDomain() }
        }
    }

    override suspend fun clear() {
        withContext(coroutineContexts.io) {
            cravingCaseDatabase.clearAllTables()
        }
    }

    private fun CravingCaseEntity.toDomain(): CravingCase {
        return CravingCase(
            strength = CravingStrength.valueOf(strength),
            caseMillis = this.millis,
        )
    }

    private fun CravingCase.toEntity(): CravingCaseEntity {
        return CravingCaseEntity(
            strength = strength.name,
            millis = caseMillis,
        )
    }
}

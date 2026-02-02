package com.shahzadian.freenic.domain.clearappdata.appclearabledata

import com.shahzadian.cravings.cravingcase.domain.ClearCravingCasesUseCase

internal class CravingCasesClearableData(
    private val clearCravingCasesUseCase: ClearCravingCasesUseCase,
) : AppClearableData() {

    override suspend fun clearData() {
        clearCravingCasesUseCase()
    }
}

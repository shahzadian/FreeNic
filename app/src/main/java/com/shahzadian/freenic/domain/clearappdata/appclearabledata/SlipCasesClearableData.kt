package com.shahzadian.freenic.domain.clearappdata.appclearabledata

import com.shahzadian.slip.slipcase.domain.ClearSlipCasesUseCase

internal class SlipCasesClearableData(
    private val clearSlipCasesUseCase: ClearSlipCasesUseCase
) : AppClearableData() {

    override suspend fun clearData() {
        clearSlipCasesUseCase()
    }
}

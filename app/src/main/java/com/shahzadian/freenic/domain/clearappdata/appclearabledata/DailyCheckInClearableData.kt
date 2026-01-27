package com.shahzadian.freenic.domain.clearappdata.appclearabledata

import com.shahzadian.dailycheckin.domain.ClearAllDailyCheckInsUseCase

internal class DailyCheckInClearableData(
    private val clearAllDailyCheckInsUseCase: ClearAllDailyCheckInsUseCase,
) : AppClearableData() {

    override suspend fun clearData() {
        clearAllDailyCheckInsUseCase()
    }
}

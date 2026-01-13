package com.shahzadian.freenic.domain.clearappdata

import com.freenic.domain.ClearAppDataUseCase
import com.shahzadian.freenic.domain.clearappdata.appclearabledata.AppClearableDataFactory
import javax.inject.Inject

internal class ClearAppDataUseCaseImpl @Inject constructor(
    private val appClearableDataFactory: AppClearableDataFactory,
) : ClearAppDataUseCase {

    override suspend fun invoke() {
        val clearableDataList = appClearableDataFactory.create()
        clearableDataList
            .forEach { it.clear() }
    }
}

package com.shahzadian.freenic.domain.clearappdata.appclearabledata

import com.shahzadian.user.domain.getuserdata.ClearUserDataUseCase

internal class UserClearableData(
    private val clearUserDataUseCase: ClearUserDataUseCase,
) : AppClearableData() {

    override suspend fun clearData() {
        clearUserDataUseCase()
    }
}
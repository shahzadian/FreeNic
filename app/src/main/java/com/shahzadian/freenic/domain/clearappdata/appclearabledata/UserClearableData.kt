package com.shahzadian.freenic.domain.clearappdata.appclearabledata

import android.content.Context
import com.freenic.CommonConst
import com.shahzadian.user.domain.getuserdata.ClearUserDataUseCase

internal class UserClearableData(
    private val context: Context,
    private val clearUserDataUseCase: ClearUserDataUseCase,
) : AppClearableData() {

    override suspend fun clearData() {
        context.getSharedPreferences(CommonConst.PREF_NAME_APP_SETTINGS, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
        clearUserDataUseCase()
    }
}

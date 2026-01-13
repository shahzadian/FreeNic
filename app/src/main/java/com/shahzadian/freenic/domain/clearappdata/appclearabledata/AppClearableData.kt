package com.shahzadian.freenic.domain.clearappdata.appclearabledata

internal abstract class AppClearableData {

    suspend fun clear() {
        clearData()
    }

    protected abstract suspend fun clearData()
}

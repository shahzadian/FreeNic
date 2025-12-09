package com.freenic.ui.appinfo

interface AppInfoHelper {

    suspend fun getAppVersion(): AppVersion
}

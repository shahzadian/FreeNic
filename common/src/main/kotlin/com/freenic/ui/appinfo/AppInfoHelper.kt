package com.freenic.ui.appinfo

interface AppInfoHelper {

    fun getAppVersion(): AppVersion

    fun isDebugBuild(): Boolean
}

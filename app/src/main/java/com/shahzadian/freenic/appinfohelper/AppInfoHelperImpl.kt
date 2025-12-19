package com.shahzadian.freenic.appinfohelper

import com.freenic.ui.appinfo.AppInfoHelper
import com.freenic.ui.appinfo.AppVersion
import com.shahzadian.freenic.BuildConfig
import javax.inject.Inject

internal class AppInfoHelperImpl @Inject constructor() : AppInfoHelper {

    override suspend fun getAppVersion(): AppVersion {
        return AppVersion(
            versionName = BuildConfig.VERSION_NAME,
            buildNumber = BuildConfig.VERSION_CODE
        )
    }

    override fun isDebugBuild(): Boolean {
        return BuildConfig.DEBUG
    }
}

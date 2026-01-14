package com.shahzadian.freenic.logger

import android.util.Log
import com.freenic.logger.AppLogger
import com.freenic.ui.appinfo.AppInfoHelper
import javax.inject.Inject

internal class AppLoggerImpl @Inject constructor(
    private val appInfoHelper: AppInfoHelper,
) : AppLogger {

    override fun logDebug(tag: String, message: String) {
        if (appInfoHelper.isDebugBuild()) {
            Log.d(tag, message)
        }
    }

    override fun logError(
        tag: String,
        message: String,
        throwable: Throwable?
    ) {
        if (appInfoHelper.isDebugBuild()) {
            Log.e(tag, message, throwable)
        }
    }
}
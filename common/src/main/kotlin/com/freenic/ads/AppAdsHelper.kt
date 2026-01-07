package com.freenic.ads

import com.freenic.ui.appinfo.AppInfoHelper
import javax.inject.Inject

interface AppAdsHelper {

    fun getAppAdsId(): String
}

internal class AppAdsHelperImpl @Inject constructor(
    private val appInfoHelper: AppInfoHelper,
) : AppAdsHelper {

    override fun getAppAdsId(): String {
        return if (appInfoHelper.isDebugBuild()) {
            ADS_TEST_ID
        } else {
            ADS_PROD_ID
        }
    }

    private companion object {
        const val ADS_TEST_ID: String = "ca-app-pub-3940256099942544/1033173712"
        const val ADS_PROD_ID: String = "ca-app-pub-3104359766953553/8805483242"
    }
}
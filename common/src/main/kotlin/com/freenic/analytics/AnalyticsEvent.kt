package com.freenic.analytics

class AnalyticsEvent(
    val name: String,
    val params: Map<String, Any>,
) {

    object EventNames {
        const val SCREEN_VIEW: String = "screen_view"
    }

    object EventParams {

//        Base params
        const val PARAM_APP_VERSION: String = "app_version"
        const val PARAM_APP_BUILD_NUMBER: String = "app_build_number"
        const val PARAM_SUBSCRIPTION_ID: String = "subscription_id"
        const val PARAM_VALUE_SUBSCRIPTION_ID_NO_SUBSCRIPTION: String = "no_subscription"
        const val PARAM_NAV_IS_BACK: String = "is_back"


        const val PARAM_SCREEN_NAME: String = "screen_name"
    }
}

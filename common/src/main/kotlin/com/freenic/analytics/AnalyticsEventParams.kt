package com.freenic.analytics

object AnalyticsEventParams {

    //        Base params
    const val PARAM_APP_VERSION: String = "app_version"
    const val PARAM_APP_BUILD_NUMBER: String = "app_build_number"
    const val PARAM_SUBSCRIPTION_ID: String = "subscription_id"
    const val PARAM_VALUE_SUBSCRIPTION_ID_NO_SUBSCRIPTION: String = "no_subscription"

    //        User registered
    const val PARAM_AGE: String = "age"
    const val PARAM_IS_QUIT_TODAY: String = "is_quit_today"
    const val PARAM_CIGS_PER_DAY: String = "cigs_per_day"
    const val PARAM_PACK_PRICE: String = "pack_price"

    //        Screen view
    const val PARAM_NAV_IS_BACK: String = "is_back"
    const val PARAM_SCREEN_NAME: String = "screen_name"
}

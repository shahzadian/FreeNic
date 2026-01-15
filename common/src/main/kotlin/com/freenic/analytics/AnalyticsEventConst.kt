package com.freenic.analytics

object AnalyticsEventConst {

    //        Base params
    const val PARAM_APP_VERSION: String = "app_version"
    const val PARAM_APP_BUILD_NUMBER: String = "app_build_number"
    const val PARAM_SUBSCRIPTION_ID: String = "subscription_id"
    const val PARAM_VALUE_SUBSCRIPTION_ID_NO_SUBSCRIPTION: String = "no_subscription"

    //        registration_finish params
    const val PARAM_AGE: String = "age"
    const val PARAM_IS_QUIT_TODAY: String = "is_quit_today"
    const val PARAM_CIGS_PER_DAY: String = "cigs_per_day"
    const val PARAM_PACK_PRICE: String = "pack_price"

    //        screen_view params
    const val PARAM_NAV_IS_BACK: String = "is_back"
    const val PARAM_SCREEN_NAME: String = "screen_name"

//    cravings_tool_tap params
    const val PARAM_CRAVING_TOOL_ID: String = "craving_tool_id"

    const val SCREEN_VIEW: String = "screen_view"
    const val REGISTRATION_START: String = "registration_start"
    const val REGISTRATION_FINISH: String = "registration_finish"
    const val DASHBOARD_WANT_TO_SMOKE_TAP: String = "dashboard_want_to_smoke_tap"
    const val DASHBOARD_I_SMOKED_TAP: String = "dashboard_i_smoked_tap"
    const val DASHBOARD_JOURNEY_CALENDAR_TAP: String = "dashboard_journey_calendar_tap"
    const val DASHBOARD_DETAILED_INSIGHTS_TAP: String = "dashboard_detailed_insights_tap"
    const val DASHBOARD_MOTIVATION_NOTE_REFRESH_TAP: String = "dashboard_motivation_note_refresh_tap"
    const val CRAVINGS_TOOL_TAP: String = "cravings_tool_tap"
}

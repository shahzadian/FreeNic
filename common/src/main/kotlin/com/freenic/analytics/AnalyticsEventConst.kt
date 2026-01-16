package com.freenic.analytics

object AnalyticsEventConst {

    //        Base params
    const val PARAM_APP_VERSION: String = "app_version"
    const val PARAM_APP_BUILD_NUMBER: String = "app_build_number"
    const val PARAM_SUBSCRIPTION_ID: String = "subscription_id"
    const val PARAM_VALUE_SUBSCRIPTION_ID_NO_SUBSCRIPTION: String = "no_subscription"

    const val REGISTRATION_FINISH_PARAM_AGE: String = "age"
    const val REGISTRATION_FINISH_PARAM_IS_QUIT_TODAY: String = "is_quit_today"
    const val REGISTRATION_FINISH_PARAM_CIGS_PER_DAY: String = "cigs_per_day"
    const val REGISTRATION_FINISH_PARAM_PACK_PRICE: String = "pack_price"

    const val SCREEN_VIEW_PARAM_NAV_IS_BACK: String = "is_back"
    const val SCREEN_VIEW_PARAM_SCREEN_NAME: String = "screen_name"

    const val CRAVINGS_TOOL_PARAM_CRAVING_TOOL_ID: String = "craving_tool_id"

    const val GOALS_ACHIEVEMENT_SHARE_PARAM_ACHIEVEMENT_ID: String = "achievement_id"

    const val SLIP_REASON_CONFIRM_PARAM_REASON_ID: String = "reason_id"
    const val SLIP_REASON_CONFIRM_PARAM_WAS_SLIP_TODAY: String = "was_slip_today"

    const val JOURNEY_CALENDAR_DAY_TAP_PARAM_HAS_EVENTS: String = "has_events"
    const val JOURNEY_CALENDAR_DAY_TAP_PARAM_DAY_TYPE: String = "day_type"
    const val JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_PAST: String = "past"
    const val JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_TODAY: String = "today"
    const val JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_FUTURE: String = "future"

    const val BREATHE_EXERCISE_FINISH_PARAM_CYCLE: String = "cycle"
    const val BREATHE_EXERCISE_FINISH_PARAM_FINISHED_STEP_STATE: String = "finished_step_state"
    const val BREATHE_EXERCISE_FINISH_PARAM_VALUE_FINISHED_STEP_STATE_READY_FOR_EXERCISE: String = "ready_for_exercise"
    const val BREATHE_EXERCISE_FINISH_PARAM_VALUE_FINISHED_STEP_STATE_INHALE: String = "inhale"
    const val BREATHE_EXERCISE_FINISH_PARAM_VALUE_FINISHED_STEP_STATE_EXHALE: String = "exhale"
    const val BREATHE_EXERCISE_FINISH_PARAM_VALUE_FINISHED_STEP_STATE_HOLD: String = "hold"

    const val GUESS_SHAPE_STARTED_PARAM_PLAY_CYCLE: String = "play_cycle"

    const val SAVINGS_GOAL_CREATED_PARAM_GOAL_PRICE: String = "goal_amount"
    const val SAVINGS_GOAL_CREATED_PARAM_CURRENT_AMOUNT: String = "current_amount"
    const val SAVINGS_GOAL_CREATED_PARAM_CATEGORY_ID: String = "category_id"

    const val SAVINGS_GOAL_SHARE_TAP_PARAM_TEMPLATE_ID: String = "template_id"

    const val ACHIEVEMENT_SHARE_TAP_PARAM_ACHIEVEMENT_ID: String = "achievement_id"
    const val ACHIEVEMENT_SHARE_TAP_PARAM_TEMPLATE_ID: String = "template_id"

    const val SCREEN_VIEW: String = "screen_view"
    const val REGISTRATION_START: String = "registration_start"
    const val REGISTRATION_FINISH: String = "registration_finish"
    const val DASHBOARD_WANT_TO_SMOKE_TAP: String = "dashboard_want_to_smoke_tap"
    const val DASHBOARD_I_SMOKED_TAP: String = "dashboard_i_smoked_tap"
    const val DASHBOARD_JOURNEY_CALENDAR_TAP: String = "dashboard_journey_calendar_tap"
    const val DASHBOARD_DETAILED_INSIGHTS_TAP: String = "dashboard_detailed_insights_tap"
    const val DASHBOARD_MOTIVATION_NOTE_REFRESH_TAP: String =
        "dashboard_motivation_note_refresh_tap"
    const val CRAVINGS_TOOL_TAP: String = "cravings_tool_tap"
    const val GOALS_SAVINGS_GOAL_CREATE_TAP: String = "goals_savings_goal_create_tap"
    const val GOALS_SAVINGS_GOAL_DETAILS_TAP: String = "goals_savings_goal_details_tap"
    const val GOALS_SAVINGS_GOAL_UPGRADE_TAP: String = "goals_savings_goal_upgrade_tap"
    const val GOALS_ACHIEVEMENT_SHARE_TAP: String = "goals_achievement_share_tap"
    const val PROFILE_QUIT_DATE_EDITED: String = "profile_quit_date_edited"
    const val PROFILE_SUBSCRIPTION_UPGRADE_TAP: String = "profile_subscription_upgrade_tap"
    const val PROFILE_CLEAR_ALL_DATA_CONFIRMATION: String = "profile_clear_all_data_confirmation"
    const val SLIP_CONFIRMATION_TAP: String = "slip_confirmation_tap"
    const val SLIP_CONFIRMATION_CANCEL_TAP: String = "slip_confirmation_cancel_tap"
    const val SLIP_REASON_CONFIRM_TAP: String = "slip_reason_confirm_tap"
    const val SLIP_REASON_SKIP_TAP: String = "slip_reason_skip_tap"
    const val SLIP_RESULT_READ_NOTES_TAP: String = "slip_result_read_notes_tap"
    const val SLIP_RESULT_BACK_TO_DASHBOARD_TAP: String = "slip_result_back_to_dashboard_tap"
    const val JOURNEY_CALENDAR_DAY_TAP: String = "journey_calendar_day_tap"
    const val BREATHE_EXERCISE_START_TAP: String = "breathe_exercise_start_tap"
    const val BREATHE_EXERCISE_FINISH_TAP: String = "breathe_exercise_finish_tap"
    const val GUESS_SHAPE_STARTED: String = "guess_shape_started"
    const val SAVINGS_GOAL_CREATED: String = "savings_goal_created"
    const val SAVINGS_GOAL_SHARE_TAP: String = "savings_goal_share_tap"
    const val SAVINGS_GOAL_DELETED: String = "savings_goal_deleted"
    const val ACHIEVEMENT_SHARE_TAP: String = "achievement_share_tap"
}

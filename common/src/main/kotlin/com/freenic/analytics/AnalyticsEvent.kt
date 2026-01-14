package com.freenic.analytics

class AnalyticsEvent(
    val name: String,
    val params: Map<String, Any>,
) {

    object EventNames {
        const val SCREEN_VIEW: String = "screen_view"
    }

    object EventParams {
        const val SCREEN_NAME: String = "screen_name"
    }
}
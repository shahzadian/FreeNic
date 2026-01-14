package com.shahzadian.freenic.analytics

import android.util.Log
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventSender

internal class AnalyticsEventSenderProd : AnalyticsEventSender {

    override fun send(event: AnalyticsEvent) {

    }
}

internal class AnalyticsEventSenderDebug : AnalyticsEventSender {

    override fun send(event: AnalyticsEvent) {
        Log.d("analytics", "Event: ${event.name}, params: ${event.params}")
    }
}

package com.shahzadian.freenic.analytics

import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventSender
import javax.inject.Inject

internal class AnalyticsEventSenderImpl @Inject constructor() : AnalyticsEventSender {

    override fun send(event: AnalyticsEvent) {
        TODO("Not yet implemented")
    }
}

package com.shahzadian.freenic.analytics

import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.logger.AppLogger

internal class AnalyticsEventSenderProd : AnalyticsEventSender {

    override fun send(event: AnalyticsEvent) {

    }
}

internal class AnalyticsEventSenderDebug(
    private val appLogger: AppLogger,
) : AnalyticsEventSender {

    override fun send(event: AnalyticsEvent) {
        appLogger.logDebug("analytics", "Event: ${event.name}, params: ${event.params}")
    }
}

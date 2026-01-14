package com.freenic.analytics

interface AnalyticsEventSender {

    fun send(event: AnalyticsEvent)
}

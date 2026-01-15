package com.freenic.analytics

class AnalyticsEvent(
    val name: String,
    val params: Map<String, AnalyticsProperty<*>> = emptyMap(),
) {

}

fun String.asAnalyticsProperty(): AnalyticsProperty<String> =
    AnalyticsProperty.StringAnalyticsProperty(this)

fun Int.asAnalyticsProperty(): AnalyticsProperty<Int> =
    AnalyticsProperty.IntAnalyticsProperty(this)

fun Boolean.asAnalyticsProperty(): AnalyticsProperty<Boolean> =
    AnalyticsProperty.BooleanAnalyticsProperty(this)

fun Float.asAnalyticsProperty(): AnalyticsProperty<Float> =
    AnalyticsProperty.FloatAnalyticsProperty(this)
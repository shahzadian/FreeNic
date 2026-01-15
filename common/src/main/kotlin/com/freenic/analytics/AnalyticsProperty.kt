package com.freenic.analytics

import android.os.Bundle

sealed class AnalyticsProperty<T> {

    abstract val value: T

    abstract fun putIntoBundle(paramName: String, bundle: Bundle)

    override fun toString(): String {
        return value.toString()
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        return value == (other as? AnalyticsProperty<*>)?.value
    }

    class StringAnalyticsProperty(override val value: String) : AnalyticsProperty<String>() {

        override fun putIntoBundle(paramName: String, bundle: Bundle) {
            bundle.putString(paramName, value)
        }
    }

    class IntAnalyticsProperty(override val value: Int) : AnalyticsProperty<Int>() {

        override fun putIntoBundle(paramName: String, bundle: Bundle) {
            bundle.putInt(paramName, value)
        }
    }

    class BooleanAnalyticsProperty(override val value: Boolean) : AnalyticsProperty<Boolean>() {

        override fun putIntoBundle(paramName: String, bundle: Bundle) {
            bundle.putBoolean(paramName, value)
        }
    }

    class FloatAnalyticsProperty(override val value: Float) : AnalyticsProperty<Float>() {

        override fun putIntoBundle(paramName: String, bundle: Bundle) {
            bundle.putFloat(paramName, value)
        }
    }
}

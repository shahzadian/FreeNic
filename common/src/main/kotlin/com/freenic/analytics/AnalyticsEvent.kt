package com.freenic.analytics

import android.os.Bundle

class AnalyticsEvent(
    val name: String,
    val params: Map<String, PropertyType<*>> = emptyMap(),
) {

    sealed class PropertyType<T> {

        abstract val value: T

        abstract fun putIntoBundle(paramName: String, bundle: Bundle)

        override fun toString(): String {
            return value.toString()
        }

        override fun hashCode(): Int {
            return value?.hashCode() ?: 0
        }

        override fun equals(other: Any?): Boolean {
            return value == (other as? PropertyType<*>)?.value
        }

        class StringProperty(override val value: String) : PropertyType<String>() {

            override fun putIntoBundle(paramName: String, bundle: Bundle) {
                bundle.putString(paramName, value)
            }
        }

        class IntProperty(override val value: Int) : PropertyType<Int>() {

            override fun putIntoBundle(paramName: String, bundle: Bundle) {
                bundle.putInt(paramName, value)
            }
        }

        class BooleanProperty(override val value: Boolean) : PropertyType<Boolean>() {

            override fun putIntoBundle(paramName: String, bundle: Bundle) {
                bundle.putBoolean(paramName, value)
            }
        }

        class FloatProperty(override val value: Float) : PropertyType<Float>() {

            override fun putIntoBundle(paramName: String, bundle: Bundle) {
                bundle.putFloat(paramName, value)
            }
        }
    }

    object EventNames {

        //        Screen view
        const val SCREEN_VIEW: String = "screen_view"

        //      Registration start
        const val EVENT_REGISTRATION_START: String = "registration_start"

        //        Registration finish
        const val EVENT_USER_REGISTERED: String = "user_registered"
    }

    object EventParams {

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
}

fun String.asAnalyticsProperty(): AnalyticsEvent.PropertyType<String> =
    AnalyticsEvent.PropertyType.StringProperty(this)

fun Int.asAnalyticsProperty(): AnalyticsEvent.PropertyType<Int> =
    AnalyticsEvent.PropertyType.IntProperty(this)

fun Boolean.asAnalyticsProperty(): AnalyticsEvent.PropertyType<Boolean> =
    AnalyticsEvent.PropertyType.BooleanProperty(this)

fun Float.asAnalyticsProperty(): AnalyticsEvent.PropertyType<Float> =
    AnalyticsEvent.PropertyType.FloatProperty(this)
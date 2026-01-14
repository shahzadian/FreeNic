package com.freenic.analytics

import android.os.Bundle

class AnalyticsEvent(
    val name: String,
    val params: Map<String, PropertyType<*>>,
) {

    object EventNames {
        const val SCREEN_VIEW: String = "screen_view"
    }

    object EventParams {

        //        Base params
        const val PARAM_APP_VERSION: String = "app_version"
        const val PARAM_APP_BUILD_NUMBER: String = "app_build_number"
        const val PARAM_SUBSCRIPTION_ID: String = "subscription_id"
        const val PARAM_VALUE_SUBSCRIPTION_ID_NO_SUBSCRIPTION: String = "no_subscription"
        const val PARAM_NAV_IS_BACK: String = "is_back"


        const val PARAM_SCREEN_NAME: String = "screen_name"
    }

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
}

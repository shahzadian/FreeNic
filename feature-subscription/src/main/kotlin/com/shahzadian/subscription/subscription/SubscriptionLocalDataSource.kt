package com.shahzadian.subscription.subscription

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal interface SubscriptionLocalDataSource {
    fun getCurrentPlanId(): String?
    fun setCurrentPlanId(planId: String?)
    fun getCurrentExpiresAtMillis(): Long?
    fun setCurrentExpiresAtMillis(expiresAtMillis: Long?)
    fun getIsAutoRenewing(): Boolean
    fun setIsAutoRenewing(isAutoRenewing: Boolean)
}

internal class SubscriptionLocalDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SubscriptionLocalDataSource {

    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun getCurrentPlanId(): String? =
        prefs.getString(KEY_PLAN_ID, null)

    override fun setCurrentPlanId(planId: String?) {
        prefs.edit().apply {
            if (planId == null) remove(KEY_PLAN_ID) else putString(KEY_PLAN_ID, planId)
        }.apply()
    }

    override fun getCurrentExpiresAtMillis(): Long? =
        if (!prefs.contains(KEY_EXPIRES_AT)) null else prefs.getLong(KEY_EXPIRES_AT, 0L)

    override fun setCurrentExpiresAtMillis(expiresAtMillis: Long?) {
        prefs.edit().apply {
            if (expiresAtMillis == null) remove(KEY_EXPIRES_AT) else putLong(
                KEY_EXPIRES_AT,
                expiresAtMillis
            )
        }.apply()
    }

    override fun getIsAutoRenewing(): Boolean {
        return prefs.getBoolean(KEY_IS_AUTO_RENEWING, false)
    }

    override fun setIsAutoRenewing(isAutoRenewing: Boolean) {
        prefs.edit().putBoolean(KEY_IS_AUTO_RENEWING, isAutoRenewing).apply()
    }

    private companion object {
        const val PREFS_NAME = "subscription_prefs"
        const val KEY_PLAN_ID = "current_plan_id"
        const val KEY_EXPIRES_AT = "current_expires_at"
        const val KEY_IS_AUTO_RENEWING = "is_auto_renewing"
    }
}

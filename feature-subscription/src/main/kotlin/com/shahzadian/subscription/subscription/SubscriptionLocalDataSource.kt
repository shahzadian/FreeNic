package com.shahzadian.subscription.subscription

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal interface SubscriptionLocalDataSource {
    fun getCurrentPlanId(): String?
    fun setCurrentPlanId(planId: String?)
}

internal class SubscriptionLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
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

    private companion object {
        const val PREFS_NAME = "subscription_prefs"
        const val KEY_PLAN_ID = "current_plan_id"
    }
}

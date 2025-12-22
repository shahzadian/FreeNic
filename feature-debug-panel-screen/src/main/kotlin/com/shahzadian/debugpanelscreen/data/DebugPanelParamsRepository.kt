package com.shahzadian.debugpanelscreen.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.debugpanelscreen.data.datastore.DebugDataStoreProvider
import com.shahzadian.debugpanelscreen.domain.DebugPanelParams
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface DebugPanelParamsRepository {

    suspend fun getParams(): DebugPanelParams?

    suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugPanelParams
}

internal class DebugPanelParamsRepositoryImpl @Inject constructor(
    private val debugDataStoreProvider: DebugDataStoreProvider,
    private val coroutineContexts: CoroutineContexts,
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
) : DebugPanelParamsRepository {

    private val dataStore by lazyUnsafe { debugDataStoreProvider.provide() }

    override suspend fun getParams(): DebugPanelParams? {
        return withContext(coroutineContexts.io) {
            val prefs = dataStore.data.firstOrNull() ?: return@withContext null
            val hasSubscription =
                prefs[booleanPreferencesKey(PARAM_KEY_HAS_PAID_SUBSCRIPTION)] ?: let {
                    getUserCurrentSubscriptionUseCase() != null
                }
            DebugPanelParams(
                hasPaidSubscription = hasSubscription,
            )
        }
    }

    override suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugPanelParams {
        return withContext(coroutineContexts.io) {
            dataStore.edit { prefs ->
                val key = booleanPreferencesKey(PARAM_KEY_HAS_PAID_SUBSCRIPTION)
                prefs[key] = isSwitchOn
            }
            return@withContext DebugPanelParams(hasPaidSubscription = isSwitchOn)
        }
    }

    private companion object {
        const val PARAM_KEY_HAS_PAID_SUBSCRIPTION = "has_paid_subscription"
    }
}

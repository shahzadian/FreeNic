package com.shahzadian.debugparams.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.debugparams.data.datastore.DebugParamsDataStoreProvider
import com.shahzadian.debugparams.domain.DebugParams
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface DebugParamsRepository {

    suspend fun getParams(): DebugParams?

    suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugParams
}

internal class DebugParamsRepositoryImpl @Inject constructor(
    private val debugDataStoreProvider: DebugParamsDataStoreProvider,
    private val coroutineContexts: CoroutineContexts,
) : DebugParamsRepository {

    private val dataStore by lazyUnsafe { debugDataStoreProvider.provide() }

    override suspend fun getParams(): DebugParams? {
        return withContext(coroutineContexts.io) {
            val prefs = dataStore.data.firstOrNull() ?: return@withContext null
            val hasSubscription =
                prefs[booleanPreferencesKey(PARAM_KEY_HAS_PAID_SUBSCRIPTION)]
                    ?: return@withContext null
            DebugParams(
                hasPaidSubscription = hasSubscription,
            )
        }
    }

    override suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugParams {
        return withContext(coroutineContexts.io) {
            dataStore.edit { prefs ->
                val key = booleanPreferencesKey(PARAM_KEY_HAS_PAID_SUBSCRIPTION)
                prefs[key] = isSwitchOn
            }
            return@withContext DebugParams(hasPaidSubscription = isSwitchOn)
        }
    }

    private companion object {
        const val PARAM_KEY_HAS_PAID_SUBSCRIPTION = "has_paid_subscription"
    }
}

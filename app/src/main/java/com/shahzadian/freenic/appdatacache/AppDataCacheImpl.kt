package com.shahzadian.freenic.appdatacache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.freenic.ui.appinfo.appdatacache.AppDataCache
import com.freenic.ui.appinfo.appdatacache.AppDataCacheUpdater
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.model.CoroutineContexts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AppDataCacheImpl @Inject constructor(
    @param:ApplicationContext
    private val context: Context,
    private val coroutineContexts: CoroutineContexts,
) : AppDataCache, AppDataCacheUpdater {

    private val Context.userDataStore by preferencesDataStore(name = DATA_STORE_NAME)
    private val dataStore: DataStore<Preferences> by lazyUnsafe {
        context.userDataStore
    }

    override suspend fun getRegisteredTimeMillis(): Long? {
        return withStoreContext {
            val prefs = dataStore.data.firstOrNull() ?: return@withStoreContext null
            prefs[PREF_KEY_REGISTERED_TIME_MILLIS]
        }

    }

    override suspend fun hasSharedAchievement(): Boolean {
        return withStoreContext {
            val prefs = dataStore.data.firstOrNull() ?: return@withStoreContext false
            prefs[PREF_KEY_HAS_SHARED_ACHIEVEMENT] ?: false
        }
    }

    override suspend fun getHasCreatedSavingsGoal(): Boolean {
        return withStoreContext {
            val prefs = dataStore.data.firstOrNull() ?: return@withStoreContext false
            prefs[PREF_KEY_HAS_CREATED_SAVINGS_GOAL] ?: false
        }
    }

    override suspend fun getSessionsCount(): Int {
        return withStoreContext {
            val prefs = dataStore.data.firstOrNull() ?: return@withStoreContext 0
            prefs[PREF_KEY_SESSIONS_COUNT] ?: 0
        }
    }

    override suspend fun setRegisteredTimeMillis(millis: Long) {
        withStoreContext {
            dataStore.edit { prefs ->
                prefs[PREF_KEY_REGISTERED_TIME_MILLIS] = millis
            }
        }
    }

    override suspend fun onAchievementShared() {
        withStoreContext {
            dataStore.edit { prefs ->
                prefs[PREF_KEY_HAS_SHARED_ACHIEVEMENT] = true
            }
        }
    }

    override suspend fun onSavingsGoalCreated() {
        withStoreContext {
            dataStore.edit { prefs ->
                prefs[PREF_KEY_HAS_CREATED_SAVINGS_GOAL] = true
            }
        }
    }

    override suspend fun setSessionsCount(sessionsCount: Int) {
        withStoreContext {
            dataStore.edit { prefs ->
                prefs[PREF_KEY_SESSIONS_COUNT] = sessionsCount
            }
        }
    }

    private suspend fun <T> withStoreContext(block: suspend CoroutineScope.() -> T): T {
        return withContext(coroutineContexts.io, block)
    }

    private companion object {
        const val DATA_STORE_NAME = "app_data_data_store"

        val PREF_KEY_REGISTERED_TIME_MILLIS = longPreferencesKey("registered_time_millis")
        val PREF_KEY_HAS_SHARED_ACHIEVEMENT = booleanPreferencesKey("has_shared_achievement")
        val PREF_KEY_HAS_CREATED_SAVINGS_GOAL = booleanPreferencesKey("has_created_savings_goal")
        val PREF_KEY_SESSIONS_COUNT = intPreferencesKey("sessions_count")
    }
}
package com.freenic.cache

import android.content.SharedPreferences
import com.freenic.CommonConst
import com.freenic.ui.model.CoroutineContexts
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

interface AppCache {

    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String, defValue: String): String?
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String, defValue: Boolean): Boolean?
    suspend fun putInt(key: String, value: Int)
    suspend fun getInt(key: String, defValue: Int): Int
    suspend fun putLong(key: String, value: Long)
    suspend fun getLong(key: String, defValue: Long): Long

}

internal class AppCacheImpl @Inject constructor(
    @param:Named(CommonConst.PREF_NAME_APP_SETTINGS)
    private val sharedPrefs: SharedPreferences,
    private val coroutineContexts: CoroutineContexts,
) : AppCache {

    override suspend fun putString(key: String, value: String) {
        withContext(coroutineContexts.io) {
            sharedPrefs.edit().putString(key, value).apply()
        }
    }

    override suspend fun getString(key: String, defValue: String): String? {
        return withContext(coroutineContexts.io) {
            sharedPrefs.getString(key, defValue)
        }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        sharedPrefs.edit().putBoolean(key, value).apply()
    }

    override suspend fun getBoolean(key: String, defValue: Boolean): Boolean {
        return withContext(coroutineContexts.io) {
            sharedPrefs.getBoolean(key, defValue)
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        withContext(coroutineContexts.io) {
            sharedPrefs.edit().putInt(key, value).apply()
        }
    }

    override suspend fun getInt(key: String, defValue: Int): Int {
        return withContext(coroutineContexts.io) {
            sharedPrefs.getInt(key, defValue)
        }
    }

    override suspend fun putLong(key: String, value: Long) {
        withContext(coroutineContexts.io) {
            sharedPrefs.edit().putLong(key, value).apply()
        }
    }

    override suspend fun getLong(key: String, defValue: Long): Long {
        return withContext(coroutineContexts.io) {
            sharedPrefs.getLong(key, defValue)
        }
    }
}
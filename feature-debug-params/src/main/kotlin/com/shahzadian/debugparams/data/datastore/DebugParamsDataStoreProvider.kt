package com.shahzadian.debugparams.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal interface DebugParamsDataStoreProvider {

    fun provide(): DataStore<Preferences>
}

internal class DebugParamsDataStoreProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : DebugParamsDataStoreProvider {

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)

    override fun provide(): DataStore<Preferences> {
        return context.dataStore
    }

    companion object {
        private const val DATA_STORE_NAME = "debug_params_data_store"
    }
}

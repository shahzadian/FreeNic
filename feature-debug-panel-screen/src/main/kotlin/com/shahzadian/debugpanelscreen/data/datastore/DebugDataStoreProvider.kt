package com.shahzadian.debugpanelscreen.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal interface DebugDataStoreProvider {

    fun provide(): DataStore<Preferences>
}

internal class DebugDataStoreProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : DebugDataStoreProvider {

    private val Context.userDataStore by preferencesDataStore(name = DATA_STORE_NAME)

    override fun provide(): DataStore<Preferences> {
        return context.userDataStore
    }

    companion object {
        private const val DATA_STORE_NAME = "debug_data_store"
    }
}

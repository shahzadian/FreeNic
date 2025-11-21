package com.shahzadian.user.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface UserDataStoreProvider {

    fun provide(): DataStore<Preferences>
}

internal class UserDataStoreProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : UserDataStoreProvider {

    private val Context.userDataStore by preferencesDataStore(name = DATA_STORE_NAME)

    override fun provide(): DataStore<Preferences> {
        return context.userDataStore
    }

    companion object {
        private const val DATA_STORE_NAME = "user_data_store"
    }
}

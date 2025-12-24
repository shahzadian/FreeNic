package com.shahzadian.savingsgoal.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal interface SavingsGoalDataStoreProvider {

    fun provide(): DataStore<Preferences>
}

internal class SavingsGoalDataStoreProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : SavingsGoalDataStoreProvider {

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)

    override fun provide(): DataStore<Preferences> {
        return context.dataStore
    }

    companion object {
        private const val DATA_STORE_NAME = "savings_goal_data_store"
    }
}
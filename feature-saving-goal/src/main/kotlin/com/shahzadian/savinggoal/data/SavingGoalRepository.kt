package com.shahzadian.savinggoal.data

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.savinggoal.data.datastore.SavingGoalDataStoreProvider
import com.shahzadian.savinggoal.domain.SavingGoal
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface SavingGoalRepository {

    suspend fun getSavingGoal(): SavingGoal?

    suspend fun onSavingGoalChanged(savingGoal: SavingGoal)
}

internal class SavingGoalRepositoryImpl @Inject constructor(
    private val savingGoalDataStoreProvider: SavingGoalDataStoreProvider,
    private val coroutineContexts: CoroutineContexts,
) : SavingGoalRepository {

    override suspend fun getSavingGoal(): SavingGoal? {
        return withContext(coroutineContexts.io) {
            val dataStore = savingGoalDataStoreProvider.provide()
            val prefs = dataStore.data.firstOrNull() ?: return@withContext null
            val name = prefs[stringPreferencesKey(PREF_KEY_NAME)] ?: return@withContext null
            val necessaryAmount =
                prefs[floatPreferencesKey(PREF_KEY_NECESSARY_AMOUNT)] ?: return@withContext null
            SavingGoal(
                name = name,
                necessaryAmount = necessaryAmount
            )
        }
    }

    override suspend fun onSavingGoalChanged(savingGoal: SavingGoal) {
        withContext(coroutineContexts.io) {
            savingGoalDataStoreProvider.provide().edit { prefs ->
                prefs[stringPreferencesKey(PREF_KEY_NAME)] = savingGoal.name
                prefs[floatPreferencesKey(PREF_KEY_NECESSARY_AMOUNT)] = savingGoal.necessaryAmount
            }
        }
    }

    private companion object {
        private const val PREF_KEY_NAME: String = "name"
        private const val PREF_KEY_NECESSARY_AMOUNT: String = "necessary_amount"
    }
}

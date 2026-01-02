package com.shahzadian.savingsgoal.data

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.savingsgoal.data.datastore.SavingsGoalDataStoreProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface SavingsGoalRepository {

    suspend fun getActiveSavingsGoal(): SavingsGoalData?

    suspend fun createSavingsGoal(savingsGoal: SavingsGoalData)

    suspend fun deleteSavingsGoal()
}

internal class SavingsGoalRepositoryImpl @Inject constructor(
    private val savingsGoalDataStoreProvider: SavingsGoalDataStoreProvider,
    private val coroutineContexts: CoroutineContexts,
) : SavingsGoalRepository {

    override suspend fun getActiveSavingsGoal(): SavingsGoalData? {
        return withContext(coroutineContexts.io) {
            val dataStore = savingsGoalDataStoreProvider.provide()
            val prefs = dataStore.data.firstOrNull() ?: return@withContext null
            val name = prefs[stringPreferencesKey(PREF_KEY_NAME)] ?: return@withContext null
            val necessaryAmount =
                prefs[floatPreferencesKey(PREF_KEY_NECESSARY_AMOUNT)] ?: return@withContext null
            val currentAmount =
                prefs[floatPreferencesKey(PREF_KEY_CURRENT_AMOUNT)] ?: 0f
            val categoryId =
                prefs[stringPreferencesKey(PREF_KEY_GOAL_CATEGORY_ID)] ?: return@withContext null
            val createdTimeMillis =
                prefs[longPreferencesKey(PREF_KEY_CREATED_TIME_MILLIS)] ?: return@withContext null
            SavingsGoalData(
                name = name,
                necessaryAmount = necessaryAmount,
                currentAmount = currentAmount,
                createTimeMillis = createdTimeMillis,
                categoryId = categoryId,
            )
        }
    }

    override suspend fun createSavingsGoal(savingsGoal: SavingsGoalData) {
        withContext(coroutineContexts.io) {
            savingsGoalDataStoreProvider.provide().edit { prefs ->
                prefs[stringPreferencesKey(PREF_KEY_NAME)] = savingsGoal.name
                prefs[floatPreferencesKey(PREF_KEY_NECESSARY_AMOUNT)] = savingsGoal.necessaryAmount
                prefs[floatPreferencesKey(PREF_KEY_CURRENT_AMOUNT)] = savingsGoal.currentAmount
                prefs[stringPreferencesKey(PREF_KEY_GOAL_CATEGORY_ID)] = savingsGoal.categoryId
                prefs[longPreferencesKey(PREF_KEY_CREATED_TIME_MILLIS)] = savingsGoal.createTimeMillis
            }
        }
    }

    override suspend fun deleteSavingsGoal() {
        withContext(coroutineContexts.io) {
            savingsGoalDataStoreProvider.provide().edit { prefs ->
                prefs.remove(stringPreferencesKey(PREF_KEY_NAME))
                prefs.remove(floatPreferencesKey(PREF_KEY_NECESSARY_AMOUNT))
                prefs.remove(floatPreferencesKey(PREF_KEY_CURRENT_AMOUNT))
                prefs.remove(stringPreferencesKey(PREF_KEY_GOAL_CATEGORY_ID))
                prefs.remove(longPreferencesKey(PREF_KEY_CREATED_TIME_MILLIS))
            }
        }
    }

    private companion object {
        private const val PREF_KEY_NAME: String = "name"
        private const val PREF_KEY_NECESSARY_AMOUNT: String = "necessary_amount"
        private const val PREF_KEY_CURRENT_AMOUNT: String = "current_amount"
        private const val PREF_KEY_GOAL_CATEGORY_ID: String = "goal_category_id"
        private const val PREF_KEY_CREATED_TIME_MILLIS: String = "created_time_millis"
    }
}

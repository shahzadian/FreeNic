package com.shahzadian.savingsgoal.data

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.savingsgoal.data.datastore.SavingsGoalDataStoreProvider
import com.shahzadian.savingsgoal.domain.SavingsGoal
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface SavingsGoalRepository {

    suspend fun getActiveSavingsGoal(): SavingsGoal?

    suspend fun onSavingsGoalsChanged(savingsGoal: SavingsGoal)
}

internal class SavingsGoalRepositoryImpl @Inject constructor(
    private val savingsGoalDataStoreProvider: SavingsGoalDataStoreProvider,
    private val coroutineContexts: CoroutineContexts,
) : SavingsGoalRepository {

    override suspend fun getActiveSavingsGoal(): SavingsGoal? {
        return withContext(coroutineContexts.io) {
            val dataStore = savingsGoalDataStoreProvider.provide()
            val prefs = dataStore.data.firstOrNull() ?: return@withContext null
            val name = prefs[stringPreferencesKey(PREF_KEY_NAME)] ?: return@withContext null
            val necessaryAmount =
                prefs[floatPreferencesKey(PREF_KEY_NECESSARY_AMOUNT)] ?: return@withContext null
            val categoryId = prefs[stringPreferencesKey(PREF_KEY_GOAL_CATEGORY_ID)] ?: return@withContext null
            SavingsGoal(
                name = name,
                necessaryAmount = necessaryAmount,
                categoryId = categoryId,
            )
        }
    }

    override suspend fun onSavingsGoalsChanged(savingsGoal: SavingsGoal) {
        withContext(coroutineContexts.io) {
            savingsGoalDataStoreProvider.provide().edit { prefs ->
                prefs[stringPreferencesKey(PREF_KEY_NAME)] = savingsGoal.name
                prefs[floatPreferencesKey(PREF_KEY_NECESSARY_AMOUNT)] = savingsGoal.necessaryAmount
            }
        }
    }

    private companion object {
        private const val PREF_KEY_NAME: String = "name"
        private const val PREF_KEY_NECESSARY_AMOUNT: String = "necessary_amount"
        private const val PREF_KEY_GOAL_CATEGORY_ID: String = "goal_category_id"
    }
}

package com.shahzadian.user.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.user.model.QuitData
import com.shahzadian.user.model.UserCigarettesData
import com.shahzadian.user.model.UserData
import com.shahzadian.user.model.UserPersonalData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val appContext: Context,
    private val coroutineContexts: CoroutineContexts,
) : UserDataRepository {

    private val Context.userDataStore by preferencesDataStore(name = DATA_STORE_NAME)

    override suspend fun isUserRegistered(): Boolean {
        return withContext(coroutineContexts.io) {
            val prefs = appContext.userDataStore.data.firstOrNull() ?: return@withContext false
            prefs[KEY_REGISTERED] ?: false
        }
    }

    override suspend fun setUserRegistered(isRegistered: Boolean) {
        withContext(coroutineContexts.io) {
            appContext.userDataStore.edit { prefs ->
                prefs[KEY_REGISTERED] = isRegistered
            }
        }
    }

    override suspend fun getUserData(): UserData? = withContext(coroutineContexts.io) {
        val prefs = appContext.userDataStore.data.firstOrNull() ?: return@withContext null

        val name = prefs[KEY_NAME]
        val age = prefs[KEY_AGE]
        val cigsPerDay = prefs[KEY_CPD]
        val packsPerDay = prefs[KEY_PPD]
        val currency = prefs[KEY_CURRENCY]
        val price = prefs[KEY_PRICE]
        val quitDate = prefs[KEY_QUIT_DATE]

        // If nothing meaningful stored, return null
        if (name.isNullOrBlank() && age == null && cigsPerDay == null && packsPerDay == null && currency.isNullOrBlank() && price == null) {
            return@withContext null
        }

        val personal = UserPersonalData(
            name = name.orEmpty(),
            age = age ?: 0,
        )
        val cigarettes = UserCigarettesData(
            cigarettesPerDay = cigsPerDay ?: 0,
            packsPerDay = packsPerDay ?: 0f,
            priceData = UserCigarettesData.PriceData(
                price = price ?: 0f,
                currency = currency.orEmpty(),
            ),
        )
        val quitData = QuitData(
            quitDateMillis = quitDate ?: 0L,
        )
        return@withContext UserData(
            personalData = personal,
            cigarettesData = cigarettes,
            quitData = quitData,
        )
    }

    override suspend fun updateUserData(userData: UserData) {
        withContext(coroutineContexts.io) {
            appContext.userDataStore.edit { prefs ->
                prefs[KEY_NAME] = userData.personalData.name
                prefs[KEY_AGE] = userData.personalData.age
                prefs[KEY_CPD] = userData.cigarettesData.cigarettesPerDay
                prefs[KEY_PPD] = userData.cigarettesData.packsPerDay
                prefs[KEY_CURRENCY] = userData.cigarettesData.priceData.currency
                prefs[KEY_PRICE] = userData.cigarettesData.priceData.price
            }
        }
    }

    private companion object {
        const val DATA_STORE_NAME = "user_data_store"
        val KEY_NAME = stringPreferencesKey("name")
        val KEY_AGE = intPreferencesKey("age")
        val KEY_CPD = intPreferencesKey("cigarettes_per_day")
        val KEY_PPD = floatPreferencesKey("packs_per_day")
        val KEY_CURRENCY = stringPreferencesKey("currency")
        val KEY_PRICE = floatPreferencesKey("price")
        val KEY_QUIT_DATE = longPreferencesKey("quit_date")
        val KEY_REGISTERED = booleanPreferencesKey("registered")
    }
}

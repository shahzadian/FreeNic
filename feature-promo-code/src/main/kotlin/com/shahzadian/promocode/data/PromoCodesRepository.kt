package com.shahzadian.promocode.data

import android.util.Log
import com.freenic.ui.extensions.lazyUnsafe
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.shahzadian.promocode.domain.PromoCodeBehavior
import com.shahzadian.promocode.domain.PromoCodeData
import org.json.JSONArray
import javax.inject.Inject

internal interface PromoCodesRepository {

    suspend fun getData(code: String): PromoCodeData?
}

internal class PromoCodesRepositoryImpl @Inject constructor() : PromoCodesRepository {

    private val config by lazyUnsafe {
        Firebase.remoteConfig
    }

    init {
        val settings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        config.setConfigSettingsAsync(settings)
        config.setDefaultsAsync(
            mapOf(KEY_PROMO_CODES to emptyList<String>())
        )
        config.fetchAndActivate()
    }

    override suspend fun getData(code: String): PromoCodeData? {
        val json = JSONArray(config.getValue(KEY_PROMO_CODES).asString())
        Log.d("LogMog", "config: $json")
        var promoCodeData: PromoCodeData? = null
        for (i in 0 until json.length()) {
            val name = json.getJSONObject(i).getString(KEY_NAME)
            Log.d("LogMog", "name: $name")
            if (name != code) continue
            val validDays = json.getJSONObject(i).getInt(KEY_VALID_DAYS)
            Log.d("LogMog", "validDays: $validDays")
            val expiration = when (validDays) {
                7 -> PromoCodeBehavior.PremiumSubscription.Expiration.Week
                30, 31 -> PromoCodeBehavior.PremiumSubscription.Expiration.Month
                365 -> PromoCodeBehavior.PremiumSubscription.Expiration.Year
                else -> continue
            }
            Log.d("LogMog", "expiration: $expiration")
            promoCodeData = PromoCodeData(
                behavior = PromoCodeBehavior.PremiumSubscription(
                    expiration = expiration
                ),
            )
        }
        Log.d("LogMog", "promoCodeData: $promoCodeData")
        return promoCodeData
    }

    private class PromoCode(
        val name: String,
        val validDays: Int,
    )

    private companion object {
        const val KEY_PROMO_CODES = "subscription_promo_codes"
        const val KEY_NAME = "name"
        const val KEY_VALID_DAYS = "valid_days"
    }
}
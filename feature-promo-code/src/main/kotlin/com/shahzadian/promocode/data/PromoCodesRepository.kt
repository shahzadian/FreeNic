package com.shahzadian.promocode.data

import com.freenic.ui.extensions.lazyUnsafe
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.shahzadian.promocode.domain.PromoCodeBehavior
import com.shahzadian.promocode.domain.PromoCodeData
import com.shahzadian.promocode.domain.PromoCodeExpiration
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
            minimumFetchIntervalInSeconds = 3600
        }
        config.setConfigSettingsAsync(settings)
        config.setDefaultsAsync(
            mapOf(KEY_PROMO_CODES to emptyList<String>())
        )
        config.fetchAndActivate()
    }

    override suspend fun getData(code: String): PromoCodeData? {
        val json = JSONArray(config.getValue(KEY_PROMO_CODES).asString())
        var promoCodeData: PromoCodeData? = null
        for (i in 0 until json.length()) {
            if (json.getString(i) == code) {
                promoCodeData = PromoCodeData(
                    behavior = PromoCodeBehavior.PremiumSubscription,
                    expiration = PromoCodeExpiration.Never,
                )
            }
        }
        return promoCodeData
    }

    private companion object {
        const val KEY_PROMO_CODES = "subscriptions_promo_codes"
    }
}
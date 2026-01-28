package com.shahzadian.subscription.paywall.ui

import android.content.SharedPreferences
import com.freenic.CommonConst
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.promocode.domain.GetPromoCodeDataUseCase
import com.shahzadian.promocode.domain.PromoCodeBehavior
import com.shahzadian.promocode.domain.PromoCodeData
import com.shahzadian.subscription.data.SubscriptionPlansRepository
import com.shahzadian.subscription.domain.SubscriptionPlan
import com.shahzadian.subscription.domain.UpdateSubscriptionUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

internal interface PromoCodeHelper {

    suspend fun onPromoCodeEntered(promoCode: String): Boolean

    suspend fun canEnterPromoCode(): Boolean
}

internal class PromoCodeHelperImpl @Inject constructor(
    @param:Named(CommonConst.PREF_NAME_APP_SETTINGS)
    private val sharedPreferences: SharedPreferences,
    private val coroutineContexts: CoroutineContexts,
    private val getPromoCodeDataUseCase: GetPromoCodeDataUseCase,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    private val subscriptionPlansRepository: SubscriptionPlansRepository,
) : PromoCodeHelper {

    private var promoCodeEnterIteration: Int = 0

    override suspend fun onPromoCodeEntered(promoCode: String): Boolean {
        promoCodeEnterIteration++
        if (promoCodeEnterIteration >= PROMO_CODE_ITERATION_LIMIT) {
            withContext(coroutineContexts.io) {
                sharedPreferences.edit()
                    .putLong(PREF_KEY_PROMO_CODE_ENTER_MILLIS, System.currentTimeMillis())
                    .apply()
            }
        }
        return getPromoCodeDataUseCase(promoCode)
            ?.let { data ->
                applyPromoCode(data)
                true
            } ?: false
    }

    override suspend fun canEnterPromoCode(): Boolean {
        return withContext(coroutineContexts.io) {
            val cachedValue = sharedPreferences.getLong(
                PREF_KEY_PROMO_CODE_ENTER_MILLIS,
                -1L
            )
            if (cachedValue == -1L) {
                if (promoCodeEnterIteration >= PROMO_CODE_ITERATION_LIMIT) {
                    return@withContext false
                } else {
                    return@withContext true
                }
            } else {
                if (cachedValue < System.currentTimeMillis() - 60 * 60 * 1000) {
                    sharedPreferences.edit().remove(PREF_KEY_PROMO_CODE_ENTER_MILLIS)
                    return@withContext true
                } else {
                    return@withContext false
                }
            }
        }
    }

    private suspend fun applyPromoCode(data: PromoCodeData) {
        when (data.behavior) {
            is PromoCodeBehavior.PremiumSubscription -> {
                val plan = subscriptionPlansRepository
                    .getPlans()
                    .first { it.paymentFrequency == (data.behavior as PromoCodeBehavior.PremiumSubscription).getPaymentFrequency() }
                updateSubscriptionUseCase(
                    plan = plan,
                )
            }
        }
    }

    private companion object {
        const val PREF_KEY_PROMO_CODE_ENTER_MILLIS: String = "promo_code_enter_millis"
        const val PROMO_CODE_ITERATION_LIMIT: Int = 3
    }

    private fun PromoCodeBehavior.PremiumSubscription.getPaymentFrequency(): SubscriptionPlan.PaymentFrequency {
        return when (this.expiration) {
            is PromoCodeBehavior.PremiumSubscription.Expiration.Month -> SubscriptionPlan.PaymentFrequency.MONTHLY
            is PromoCodeBehavior.PremiumSubscription.Expiration.Year -> SubscriptionPlan.PaymentFrequency.ANNUAL
        }
    }
}
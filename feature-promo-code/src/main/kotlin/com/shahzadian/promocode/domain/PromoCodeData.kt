package com.shahzadian.promocode.domain

class PromoCodeData(
    val behavior: PromoCodeBehavior,
)

sealed interface PromoCodeBehavior {

    data class PremiumSubscription(
        val expiration: Expiration,
    ) : PromoCodeBehavior {

        sealed interface Expiration {
            object Month : Expiration
            object Year : Expiration
        }
    }
}

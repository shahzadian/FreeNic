package com.shahzadian.promocode.domain

class PromoCodeData(
    val behavior: PromoCodeBehavior,
    val expiration: PromoCodeExpiration,
)

sealed interface PromoCodeBehavior {

    data object PremiumSubscription : PromoCodeBehavior
}

sealed interface PromoCodeExpiration {

    data object Never : PromoCodeExpiration
}

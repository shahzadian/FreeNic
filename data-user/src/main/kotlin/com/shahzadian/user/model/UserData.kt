package com.shahzadian.user.model

import java.util.Currency
import java.util.Locale

class UserData(
    val personalData: UserPersonalData,
    val cigarettesData: UserCigarettesData,
    val quitData: QuitData,
)

class UserPersonalData(
    val name: String,
    val age: Int,
)

class UserCigarettesData(
    val cigarettesPerDay: Int,
    val packsPerDay: Float,
    val priceData: PriceData,
) {

    class PriceData(
        val price: Float,
        val currency: String,
    ) {

        fun isValidCurrency(): Boolean {
            return getAvailableCurrencies().contains(currency)
        }

        companion object {
            fun getAvailableCurrencies(): List<String> {
                return listOf(
                    Currency.getInstance(Locale.US).currencyCode,
                    Currency.getInstance(Locale.UK).currencyCode,
                    Currency.getInstance(Locale.FRANCE).currencyCode,
                    "RUB"
                )
            }
        }
    }
}

class QuitData(
    val quitDateMillis: Long,
)

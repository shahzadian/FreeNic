package com.shahzadian.user.model

class UserData(
    val personalData: UserPersonalData,
    val cigarettesData: UserCigarettesData,
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
    )
}

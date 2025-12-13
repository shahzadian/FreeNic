package com.shahzadian.user.domain

import com.shahzadian.user.model.QuitData
import com.shahzadian.user.model.UserCigarettesData
import com.shahzadian.user.model.UserData
import com.shahzadian.user.model.UserPersonalData
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.random.Random

@RunWith(JUnit4::class)
class UserDataHelperImplTest {

    @Test
    fun testGetSkippedDaysCount() {
        val quitDays = Random.nextInt(0, 100)
        val userData = getUserData(
            quitDays = quitDays
        )
        val userDataHelper = UserDataHelperImpl(userData)
        val skippedDays = userDataHelper.getSkippedDaysCount()
        assert(skippedDays == quitDays)
    }

    @Test
    fun testGetSkippedCigsCount() {
        val cigsPerDay = Random.nextInt(0, 100)
        val quitDays = 10
        val userData = getUserData(
            cigsPerDay = cigsPerDay,
            quitDays = quitDays,
        )
        val userDataHelper = UserDataHelperImpl(userData)
        val skippedCigs = userDataHelper.getSkippedCigsCount()
        assert(skippedCigs == cigsPerDay * quitDays)
    }

    @Test
    fun testGetSavedMoney() {
        val quitDays = 10
        val cigsPerDay = 20
        val price = 10f
        val userData = getUserData(
            quitDays = quitDays,
            cigsPerDay = cigsPerDay,
            price = price
        )
        val userDataHelper = UserDataHelperImpl(userData)
        val savedMoney = userDataHelper.getSavedMoney()
        assert(savedMoney == cigsPerDay.toFloat() / 20 * price * quitDays)
    }

    private fun getUserData(
        quitDays: Int = 10,
        cigsPerDay: Int = 20,
        price: Float = 10f,
    ): UserData {
        return UserData(
            personalData = UserPersonalData(
                name = "Some name",
                age = 25,
            ),
            cigarettesData = UserCigarettesData(
                cigarettesPerDay = cigsPerDay,
                packsPerDay = cigsPerDay / 20f,
                priceData = UserCigarettesData.PriceData(
                    price = price,
                    currency = "USD",
                ),
            ),
            quitData = QuitData(
                quitDateMillis = System.currentTimeMillis() - (60 * 60 * 24 * 1000 * quitDays.toLong())
            )
        )
    }
}

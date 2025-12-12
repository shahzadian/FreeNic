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

    private fun getUserData(
        quitDays: Int = 10,
        cigsPerDay: Int = 20,
    ): UserData {
        return UserData(
            personalData = UserPersonalData(
                name = "Some name",
                age = 25,
            ),
            cigarettesData = UserCigarettesData(
                cigarettesPerDay = cigsPerDay,
                packsPerDay = 1.5f,
                priceData = UserCigarettesData.PriceData(
                    price = 10f,
                    currency = "USD",
                ),
            ),
            quitData = QuitData(
                quitDateMillis = System.currentTimeMillis() - (60 * 60 * 24 * quitDays.toLong())
            )
        )
    }
}

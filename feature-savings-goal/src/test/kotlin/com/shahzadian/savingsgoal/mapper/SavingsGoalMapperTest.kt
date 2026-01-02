package com.shahzadian.savingsgoal.mapper

import com.shahzadian.savingsgoal.data.SavingsGoalData
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.QuitData
import com.shahzadian.user.model.UserCigarettesData
import com.shahzadian.user.model.UserData
import com.shahzadian.user.model.UserPersonalData
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.Currency

@RunWith(JUnit4::class)
internal class SavingsGoalMapperTest {

    private fun buildUserData(currencyCode: String = "USD"): UserData {
        return UserData(
            personalData = UserPersonalData(
                name = "John",
                age = 30
            ),
            cigarettesData = UserCigarettesData(
                cigarettesPerDay = 10,
                packsPerDay = 1f,
                priceData = UserCigarettesData.PriceData(
                    price = 10f,
                    currency = currencyCode
                )
            ),
            quitData = QuitData(
                quitDateMillis = System.currentTimeMillis() - 3L * 24 * 60 * 60 * 1000 // 3 days ago
            )
        )
    }

    @Test
    fun createDomainModel_calculatesFieldsCorrectly() = runBlocking {
        // Arrange
        val savedPerDay = 10f
        val noCigDays = 5
        val amount = 100f
        val name = "New Goal"
        val categoryId = "cat-1"

        val userData = buildUserData("USD")
        val currencySymbol = Currency.getInstance("USD").symbol

        val userDataHelperFactory = mock(UserDataHelperFactory::class.java)
        val getUserDataUseCase = mock(GetUserDataUseCase::class.java)
        val helper = mock(UserDataHelper::class.java)

        Mockito.`when`(getUserDataUseCase.invoke()).thenReturn(userData)
        Mockito.`when`(userDataHelperFactory.create(userData)).thenReturn(helper)
        Mockito.`when`(helper.getSavedMoneyPerDay()).thenReturn(savedPerDay)
        Mockito.`when`(helper.getSkippedDaysCount()).thenReturn(noCigDays)

        val mapper = SavingsGoalMapperImpl(
            userDataHelperFactory = userDataHelperFactory,
            getUserDataUseCase = getUserDataUseCase
        )

        // Act
        val result = mapper.createDomainModel(name, amount, categoryId, currentAmount = 0f)

        // Assert
        assert(result.name == name)
        assert(result.necessaryAmount == amount)
        // created now -> passedDays = 0
        assert(result.currentAmount == 0f)
        assert(result.amountLeft == amount)
        assert(result.achievedPercents == 0f)
        assert(result.daysLeft == (amount / savedPerDay).toInt())
        assert(result.noCigDays == noCigDays)
        assert(result.currency == currencySymbol)
        assert(result.categoryId == categoryId)
        // createTimeMillis should be close to now (within 2s)
        val now = System.currentTimeMillis()
        assert(kotlin.math.abs(now - result.createTimeMillis) < 2000)
    }

    @Test
    fun createDataModel_thenFromDataToDomain_calculatesFieldsCorrectly() = runBlocking {
        // Arrange
        val savedPerDay = 20f
        val noCigDays = 7
        val amount = 200f
        val name = "My Goal"
        val categoryId = "cat-2"

        val userData = buildUserData("USD")
        val currencySymbol = Currency.getInstance("USD").symbol

        val userDataHelperFactory = mock(UserDataHelperFactory::class.java)
        val getUserDataUseCase = mock(GetUserDataUseCase::class.java)
        val helper = mock(UserDataHelper::class.java)

        Mockito.`when`(getUserDataUseCase.invoke()).thenReturn(userData)
        Mockito.`when`(userDataHelperFactory.create(userData)).thenReturn(helper)
        Mockito.`when`(helper.getSavedMoneyPerDay()).thenReturn(savedPerDay)
        Mockito.`when`(helper.getSkippedDaysCount()).thenReturn(noCigDays)

        val mapper = SavingsGoalMapperImpl(
            userDataHelperFactory = userDataHelperFactory,
            getUserDataUseCase = getUserDataUseCase
        )

        // Act
        val data = mapper.createDataModel(name, amount, categoryId, currentAmount = 0f)
        val domain = mapper.fromDataToDomain(data)

        // Assert
        assert(data.name == name)
        assert(data.necessaryAmount == amount)
        assert(data.categoryId == categoryId)
        val now = System.currentTimeMillis()
        assert(kotlin.math.abs(now - data.createTimeMillis) < 2000)

        // Since createTime is ~ now, passedDays == 0
        assert(domain.currentAmount == 0f)
        assert(domain.amountLeft == amount)
        assert(domain.daysLeft == (amount / savedPerDay).toInt())
        assert(domain.achievedPercents == 0f)
        assert(domain.noCigDays == noCigDays)
        assert(domain.currency == currencySymbol)
    }

    @Test
    fun fromDataToDomain_withPastCreateTime_calculatesProgressCorrectly() = runBlocking {
        // Arrange
        val savedPerDay = 20f
        val noCigDays = 4
        val necessary = 250f
        val name = "Trip"
        val categoryId = "cat-3"

        val userData = buildUserData("USD")
        val currencySymbol = Currency.getInstance("USD").symbol
        val helper = mock(UserDataHelper::class.java)
        val userDataHelperFactory = mock(UserDataHelperFactory::class.java)
        val getUserDataUseCase = mock(GetUserDataUseCase::class.java)

        Mockito.`when`(getUserDataUseCase.invoke()).thenReturn(userData)
        Mockito.`when`(userDataHelperFactory.create(userData)).thenReturn(helper)
        Mockito.`when`(helper.getSavedMoneyPerDay()).thenReturn(savedPerDay)
        Mockito.`when`(helper.getSkippedDaysCount()).thenReturn(noCigDays)

        val mapper = SavingsGoalMapperImpl(
            userDataHelperFactory = userDataHelperFactory,
            getUserDataUseCase = getUserDataUseCase
        )

        val daysAgo = 5
        val createTimeMillis = System.currentTimeMillis() - daysAgo * 24L * 60 * 60 * 1000
        val data = SavingsGoalData(
            name = name,
            necessaryAmount = necessary,
            currentAmount = 50f,
            createTimeMillis = createTimeMillis,
            categoryId = categoryId
        )

        // Act
        val domain = mapper.fromDataToDomain(data)

        // Assert
        val expectedSaved = 50f + savedPerDay * daysAgo
        val expectedPercents = (expectedSaved / necessary) * 100f
        val expectedMoneyLeft = (necessary - expectedSaved).coerceAtLeast(0f)
        val expectedDaysLeft = (expectedMoneyLeft / savedPerDay).toInt().coerceAtLeast(0)

        assert(domain.name == name)
        assert(domain.currentAmount == expectedSaved)
        assert(domain.achievedPercents == kotlin.math.min(expectedPercents, 100f))
        assert(domain.amountLeft == expectedMoneyLeft)
        assert(domain.daysLeft == expectedDaysLeft)
        assert(domain.noCigDays == noCigDays)
        assert(domain.currency == currencySymbol)
        assert(domain.categoryId == categoryId)
    }
}

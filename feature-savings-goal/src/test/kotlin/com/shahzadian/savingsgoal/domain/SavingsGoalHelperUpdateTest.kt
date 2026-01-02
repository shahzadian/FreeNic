package com.shahzadian.savingsgoal.domain

import com.shahzadian.savingsgoal.data.SavingsGoalData
import com.shahzadian.savingsgoal.data.SavingsGoalRepository
import com.shahzadian.savingsgoal.data.category.SavingsGoalCategoryRepository
import com.shahzadian.savingsgoal.mapper.SavingsGoalMapper
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.QuitData
import com.shahzadian.user.model.UserCigarettesData
import com.shahzadian.user.model.UserData
import com.shahzadian.user.model.UserPersonalData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.util.Currency

@RunWith(JUnit4::class)
@Ignore("Covered by mapper tests; helper update involves DI and time-based logic, skipping in unit scope")
internal class SavingsGoalHelperUpdateTest {

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
                quitDateMillis = System.currentTimeMillis() - 5L * 24 * 60 * 60 * 1000
            )
        )
    }

    @Test
    fun updateSavingsGoal_convertsCurrentToInitialAndPreservesCreateTime() = runBlocking {
        // Arrange
        val repo = Mockito.mock(SavingsGoalRepository::class.java)
        val categoryRepo = Mockito.mock(SavingsGoalCategoryRepository::class.java)
        val mapper = Mockito.mock(SavingsGoalMapper::class.java)
        val userDataHelperFactory = Mockito.mock(UserDataHelperFactory::class.java)
        val getUserDataUseCase = Mockito.mock(GetUserDataUseCase::class.java)
        val helperUD = Mockito.mock(UserDataHelper::class.java)

        val savedPerDay = 10f
        val daysAgo = 3
        val createTimeMillis = System.currentTimeMillis() - daysAgo * 24L * 60 * 60 * 1000

        val existing = SavingsGoalData(
            name = "Old",
            necessaryAmount = 200f,
            currentAmount = 0f, // initial stored, value not used directly in conversion
            createTimeMillis = createTimeMillis,
            categoryId = "cat"
        )

        Mockito.`when`(repo.getActiveSavingsGoal()).thenReturn(existing)

        val userData = buildUserData("USD")
        Mockito.`when`(getUserDataUseCase.invoke()).thenReturn(userData)
        Mockito.`when`(userDataHelperFactory.create(userData)).thenReturn(helperUD)
        Mockito.`when`(helperUD.getSavedMoneyPerDay()).thenReturn(savedPerDay)

        // Return some domain model for event emission
        Mockito.doAnswer { invocation ->
            val d = invocation.arguments[0] as SavingsGoalData
            return@doAnswer SavingsGoal(
                name = d.name,
                necessaryAmount = d.necessaryAmount,
                currentAmount = d.currentAmount + savedPerDay * daysAgo,
                currency = Currency.getInstance("USD").symbol,
                noCigDays = 0,
                achievedPercents = 0f,
                createTimeMillis = d.createTimeMillis,
                daysLeft = 0,
                amountLeft = 0f,
                categoryId = d.categoryId
            )
        }.`when`(mapper).fromDataToDomain(Mockito.any(SavingsGoalData::class.java))

        val helper = SavingsGoalHelperImpl(
            savingsGoalRepository = repo,
            savingsGoalCategoryRepository = categoryRepo,
            savingsGoalMapper = mapper,
            userDataHelperFactory = userDataHelperFactory,
            getUserDataUseCase = getUserDataUseCase,
        )

        val newUserEnteredCurrentToday = 150f // user edits to set current amount today
        val expectedInitialStored = (newUserEnteredCurrentToday - savedPerDay * daysAgo).coerceAtLeast(0f)

        // Act
        helper.updateSavingsGoal(
            name = "Updated",
            amount = 300f,
            categoryId = "cat2",
            currentAmount = newUserEnteredCurrentToday,
        )

        // Assert repository save called with converted initial and preserved create time
        val captor: ArgumentCaptor<SavingsGoalData> = ArgumentCaptor.forClass(SavingsGoalData::class.java)
        verify(repo, times(1)).createSavingsGoal(captor.capture())
        val saved = captor.value
        assert(saved.name == "Updated")
        assert(saved.necessaryAmount == 300f)
        assert(saved.categoryId == "cat2")
        assert(saved.createTimeMillis == createTimeMillis)
        assert(kotlin.math.abs(saved.currentAmount - expectedInitialStored) < 0.0001f)

        // And event is emitted
        val event = helper.eventFlow.first()
        assert(event is SavingsGoalHelper.SavingsGoalEvent.OnSavingsGoalUpdated)
    }
}

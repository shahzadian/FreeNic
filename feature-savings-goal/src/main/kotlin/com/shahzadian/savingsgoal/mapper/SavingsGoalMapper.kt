package com.shahzadian.savingsgoal.mapper

import com.shahzadian.savingsgoal.data.SavingsGoalData
import com.shahzadian.savingsgoal.domain.SavingsGoal
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import javax.inject.Inject

interface SavingsGoalMapper {

    fun fromDomainToData(savingsGoal: SavingsGoal): SavingsGoalData

    suspend fun fromDataToDomain(savingsGoalData: SavingsGoalData): SavingsGoal

    suspend fun createDomainModel(
        name: String,
        amount: Float,
        categoryId: String
    ): SavingsGoal

    suspend fun createDataModel(
        name: String,
        amount: Float,
        categoryId: String
    ): SavingsGoalData
}

internal class SavingsGoalMapperImpl @Inject constructor(
    private val userDataHelperFactory: UserDataHelperFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
) : SavingsGoalMapper {

    private var _userData: UserData? = null
    private var _userDataHelper: UserDataHelper? = null

    override fun fromDomainToData(savingsGoal: SavingsGoal): SavingsGoalData {
        return SavingsGoalData(
            name = savingsGoal.name,
            necessaryAmount = savingsGoal.necessaryAmount,
            createTimeMillis = savingsGoal.createTimeMillis,
            categoryId = savingsGoal.categoryId
        )
    }

    override suspend fun fromDataToDomain(savingsGoalData: SavingsGoalData): SavingsGoal {
        val necessaryMoney = savingsGoalData.necessaryAmount
        val savedMoneyPerDay = getUserDataHelper().getSavedMoneyPerDay()
        val passedDays = getPassedDaysFromMillis(savingsGoalData.createTimeMillis)
        val savedMoney = savedMoneyPerDay * passedDays
        val percents = (savedMoney / necessaryMoney) * 100
        val currency = getUserData().cigarettesData.priceData.getValidCurrencyOrNull()?.symbol
        val noCigDays = getUserDataHelper().getSkippedDaysCount()
        val moneyLeft = necessaryMoney - savedMoney
        val daysLeft = (moneyLeft / savedMoneyPerDay).toInt()
        return SavingsGoal(
            name = savingsGoalData.name,
            necessaryAmount = savingsGoalData.necessaryAmount,
            currentAmount = savedMoney,
            currency = currency,
            noCigDays = noCigDays,
            achievedPercents = minOf(percents, 100f),
            createTimeMillis = savingsGoalData.createTimeMillis,
            daysLeft = maxOf(0, daysLeft),
            amountLeft = maxOf(0f, moneyLeft),
            categoryId = savingsGoalData.categoryId
        )
    }

    override suspend fun createDomainModel(
        name: String,
        amount: Float,
        categoryId: String
    ): SavingsGoal {
        val createTimeMillis = System.currentTimeMillis()
        val savedMoneyPerDay = getUserDataHelper().getSavedMoneyPerDay()
        val passedDays = getPassedDaysFromMillis(createTimeMillis)
        val savedMoney = savedMoneyPerDay * passedDays
        val currency = getUserData().cigarettesData.priceData.getValidCurrencyOrNull()?.symbol
        val daysLeft = (amount / savedMoneyPerDay).toInt()
        return SavingsGoal(
            name = name,
            necessaryAmount = amount,
            currentAmount = savedMoney,
            currency = currency,
            noCigDays = getUserDataHelper().getSkippedDaysCount(),
            achievedPercents = 0f,
            daysLeft = maxOf(0, daysLeft),
            createTimeMillis = createTimeMillis,
            amountLeft = amount,
            categoryId = categoryId
        )
    }

    override suspend fun createDataModel(
        name: String,
        amount: Float,
        categoryId: String
    ): SavingsGoalData {
        return SavingsGoalData(
            name = name,
            necessaryAmount = amount,
            createTimeMillis = System.currentTimeMillis(),
            categoryId = categoryId,
        )
    }

    private suspend fun getUserData(): UserData {
        return _userData ?: let {
            val userData = getUserDataUseCase()
                ?: throw IllegalStateException("User data is null")
            this._userData = userData
            userData
        }
    }

    private suspend fun getUserDataHelper(): UserDataHelper {
        return _userDataHelper ?: let {
            val userDataHelper = userDataHelperFactory.create(getUserData())
            this._userDataHelper = userDataHelper
            userDataHelper
        }
    }

    private fun getPassedDaysFromMillis(millis: Long): Int {
        val passedMillis = System.currentTimeMillis() - millis
        return (passedMillis / (1000 * 60 * 60 * 24)).toInt()
    }
}

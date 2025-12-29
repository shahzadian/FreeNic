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

    suspend fun createDataModel(
        name: String,
        amount: Double,
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
            categoryId = savingsGoal.categoryId
        )
    }

    override suspend fun fromDataToDomain(savingsGoalData: SavingsGoalData): SavingsGoal {
        val savedMoney = getUserDataHelper().getSavedMoney()
        val necessaryMoney = savingsGoalData.necessaryAmount
        val percents = (savedMoney / necessaryMoney) * 100
        val currency = getUserData().cigarettesData.priceData.getValidCurrencyOrNull()?.symbol
        val noCigDays = getUserDataHelper().getSkippedDaysCount()
        val moneyLeft = necessaryMoney - savedMoney
        val savedMoneyPerDay = getUserDataHelper().getSavedMoneyPerDay()
        val daysLeft = (moneyLeft / savedMoneyPerDay).toInt()
        return SavingsGoal(
            name = savingsGoalData.name,
            necessaryAmount = savingsGoalData.necessaryAmount,
            currentAmount = savedMoney,
            currency = currency,
            noCigDays = noCigDays,
            achievedPercents = minOf(percents, 100f),
            daysLeft = maxOf(0, daysLeft),
            amountLeft = maxOf(0f, moneyLeft),
            categoryId = savingsGoalData.categoryId
        )
    }

    override suspend fun createDataModel(
        name: String,
        amount: Double,
        categoryId: String
    ): SavingsGoalData {
        return SavingsGoalData(
            name = name,
            necessaryAmount = amount.toFloat(),
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
}

package com.shahzadian.savingsgoal.mapper

import com.shahzadian.savingsgoal.data.SavingsGoalData
import com.shahzadian.savingsgoal.domain.SavingsGoal
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import javax.inject.Inject

interface SavingsGoalMapper {

    fun fromDomainToData(savingsGoal: SavingsGoal): SavingsGoalData

    suspend fun fromDataToDomain(savingsGoalData: SavingsGoalData): SavingsGoal

    suspend fun createDomain(
        name: String,
        amount: Double,
        categoryId: String
    ): SavingsGoal
}

internal class SavingsGoalMapperImpl @Inject constructor(
    private val userDataHelperFactory: UserDataHelperFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
) : SavingsGoalMapper {

    override fun fromDomainToData(savingsGoal: SavingsGoal): SavingsGoalData {
        return SavingsGoalData(
            name = savingsGoal.name,
            necessaryAmount = savingsGoal.necessaryAmount,
            categoryId = savingsGoal.categoryId
        )
    }

    override suspend fun fromDataToDomain(savingsGoalData: SavingsGoalData): SavingsGoal {
        val savedMoney = getSavedMoney()
        val necessaryMoney = savingsGoalData.necessaryAmount
        val percents = (savedMoney / necessaryMoney) * 100
        return SavingsGoal(
            name = savingsGoalData.name,
            necessaryAmount = savingsGoalData.necessaryAmount,
            currentAmount = savedMoney,
            achievedPercents = 100 - percents,
            categoryId = savingsGoalData.categoryId
        )
    }

    override suspend fun createDomain(
        name: String,
        amount: Double,
        categoryId: String
    ): SavingsGoal {
        return SavingsGoal(
            name = name,
            necessaryAmount = amount.toFloat(),
            categoryId = categoryId,
            currentAmount = getSavedMoney(),
            achievedPercents = 0f,
        )
    }

    private suspend fun getSavedMoney(): Float {
        val userData = getUserDataUseCase()
            ?: throw IllegalStateException("User data is null")
        val userDataHelper = userDataHelperFactory.create(userData)
        return userDataHelper.getSavedMoney()
    }
}

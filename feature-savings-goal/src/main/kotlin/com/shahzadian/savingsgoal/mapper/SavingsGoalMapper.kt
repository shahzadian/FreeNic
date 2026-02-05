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
        categoryId: String,
        currentAmount: Float,
    ): SavingsGoal

    suspend fun createDataModel(
        name: String,
        amount: Float,
        categoryId: String,
        currentAmount: Float,
    ): SavingsGoalData
}

internal class SavingsGoalMapperImpl @Inject constructor(
    private val userDataHelperFactory: UserDataHelperFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
) : SavingsGoalMapper {

    override fun fromDomainToData(savingsGoal: SavingsGoal): SavingsGoalData {
        return SavingsGoalData(
            name = savingsGoal.name,
            necessaryAmount = savingsGoal.necessaryAmount,
            currentAmount = savingsGoal.currentAmount,
            createTimeMillis = savingsGoal.createTimeMillis,
            categoryId = savingsGoal.categoryId
        )
    }

    override suspend fun fromDataToDomain(savingsGoalData: SavingsGoalData): SavingsGoal {
        val necessaryMoney = savingsGoalData.necessaryAmount
        val savedMoneyPerDay = getUserDataHelper().getSavedMoneyPerDay()
        val passedDays = getPassedDaysFromMillis(savingsGoalData.createTimeMillis)
        val currentAmount = savingsGoalData.currentAmount + savedMoneyPerDay * passedDays
        val percents = (currentAmount / necessaryMoney) * 100
        val currency = getUserData().cigarettesData.priceData.getValidCurrencyOrNull()?.symbol
        val noCigDays = getUserDataHelper().getSkippedDaysCount()
        val moneyLeft = necessaryMoney - currentAmount
        val daysLeft = (moneyLeft / savedMoneyPerDay).toInt()
        val displayableCurrency = currency ?: ""
        val displayableCurrentAmount =
            getDisplayableAmount(currentAmount.coerceAtLeast(0f).toDouble())
        val displayableNecessaryAmount =
            getDisplayableAmount(necessaryMoney.coerceAtLeast(0f).toDouble())
        val displayableAmountLeft = getDisplayableAmount(moneyLeft.coerceAtLeast(0f).toDouble())
        return SavingsGoal(
            name = savingsGoalData.name,
            necessaryAmount = savingsGoalData.necessaryAmount,
            currentAmount = currentAmount.coerceAtLeast(0f),
            displayableCurrentAmount = "$displayableCurrentAmount$displayableCurrency",
            displayableNecessaryAmount = "$displayableNecessaryAmount$displayableCurrency",
            currency = currency,
            noCigDays = noCigDays,
            achievedPercents = minOf(percents, 100f),
            createTimeMillis = savingsGoalData.createTimeMillis,
            daysLeft = maxOf(0, daysLeft),
            amountLeft = maxOf(0f, moneyLeft),
            displayableAmountLeft = "$displayableAmountLeft$displayableCurrency",
            categoryId = savingsGoalData.categoryId
        )
    }

    override suspend fun createDomainModel(
        name: String,
        amount: Float,
        categoryId: String,
        currentAmount: Float,
    ): SavingsGoal {
        val createTimeMillis = System.currentTimeMillis()
        val savedMoneyPerDay = getUserDataHelper().getSavedMoneyPerDay()
        // При создании цели текущая сумма равна введённой пользователем начальной сумме
        val computedCurrent = currentAmount
        val currency = getUserData().cigarettesData.priceData.getValidCurrencyOrNull()?.symbol
        val amountLeft = (amount - computedCurrent).coerceAtLeast(0f)
        val daysLeft = (amountLeft / savedMoneyPerDay).toInt()
        val achievedPercents =
            if (amount == 0f) 0f else ((computedCurrent / amount) * 100f).coerceAtMost(100f)
        val displayableCurrency = currency ?: ""
        val displayableCurrentAmount =
            getDisplayableAmount(currentAmount.coerceAtLeast(0f).toDouble())
        val displayableNecessaryAmount = getDisplayableAmount(amount.coerceAtLeast(0f).toDouble())
        val displayableAmountLeft = getDisplayableAmount(amountLeft.coerceAtLeast(0f).toDouble())
        return SavingsGoal(
            name = name,
            necessaryAmount = amount,
            currentAmount = computedCurrent.coerceAtLeast(0f),
            displayableCurrentAmount = "$displayableCurrentAmount$displayableCurrency",
            displayableNecessaryAmount = "$displayableNecessaryAmount$displayableCurrency",
            currency = currency,
            noCigDays = getUserDataHelper().getSkippedDaysCount(),
            achievedPercents = achievedPercents,
            daysLeft = maxOf(0, daysLeft),
            createTimeMillis = createTimeMillis,
            amountLeft = amountLeft,
            displayableAmountLeft = "$displayableAmountLeft$displayableCurrency",
            categoryId = categoryId
        )
    }

    override suspend fun createDataModel(
        name: String,
        amount: Float,
        categoryId: String,
        currentAmount: Float,
    ): SavingsGoalData {
        return SavingsGoalData(
            name = name,
            necessaryAmount = amount,
            currentAmount = currentAmount,
            createTimeMillis = System.currentTimeMillis(),
            categoryId = categoryId,
        )
    }

    private suspend fun getUserData(): UserData {
        return getUserDataUseCase()
            ?: throw IllegalStateException("User data is null")
    }

    private suspend fun getUserDataHelper(): UserDataHelper {
        return userDataHelperFactory.create(getUserData())
    }

    private fun getPassedDaysFromMillis(millis: Long): Int {
        val passedMillis = System.currentTimeMillis() - millis
        return (passedMillis / (1000 * 60 * 60 * 24)).toInt()
    }

    private fun getDisplayableAmount(amount: Double): String {
        val scales = listOf(
            MoneyScale(1_000_000_000_000.0, "T"),
            MoneyScale(1_000_000_000.0, "B"),
            MoneyScale(1_000_000.0, "M"),
            MoneyScale(1_000.0, "K")
        )
        val absAmount = kotlin.math.abs(amount)

        for (scale in scales) {
            if (absAmount >= scale.value) {
                val scaled = amount / scale.value
                return "${formatNumber(scaled, 2)} ${scale.suffix}"
            }
        }

        return formatNumber(amount, 0)
    }

    private fun formatNumber(
        value: Double,
        maxFractionDigits: Int
    ): String {
        val formatter = java.text.DecimalFormat().apply {
            roundingMode = java.math.RoundingMode.HALF_UP
            minimumFractionDigits = 0
            this.maximumFractionDigits = maxFractionDigits
        }
        return formatter.format(value)
    }

    private class MoneyScale(
        val value: Double,
        val suffix: String
    )
}

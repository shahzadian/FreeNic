package com.shahzadian.user.domain

import com.shahzadian.user.model.UserData
import javax.inject.Inject

interface UserDataHelper {

    fun getSkippedCigsCount(): Int

    fun getSkippedDaysCount(): Int

    fun getSavedMoney(): Float

    fun getSavedMoneyPerDay(): Float
}

internal class UserDataHelperImpl(
    private val userData: UserData
) : UserDataHelper {

    private var skippedDaysCount: Int? = null

    override fun getSkippedCigsCount(): Int {
        return resolveSkippedDays() * userData.cigarettesData.cigarettesPerDay
    }

    override fun getSkippedDaysCount(): Int {
        return resolveSkippedDays()
    }

    override fun getSavedMoney(): Float {
        val skippedDaysCount = resolveSkippedDays()
        return skippedDaysCount * getSavedMoneyPerDay()
    }

    override fun getSavedMoneyPerDay(): Float {
        val packsPerDay = userData.cigarettesData.packsPerDay
        val pricePerPack = userData.cigarettesData.priceData.price / packsPerDay
        return pricePerPack * packsPerDay
    }

    private fun resolveSkippedDays(): Int {
        return skippedDaysCount ?: let {
            val quitMillis = userData.quitData.quitDateMillis
            val todayMillis = System.currentTimeMillis()
            val delta = todayMillis - quitMillis
            val skippedDays = (delta / (1000 * 60 * 60 * 24)).toInt()
            skippedDaysCount = skippedDays
            skippedDays
        }
    }
}

interface UserDataHelperFactory {
    fun create(userData: UserData): UserDataHelper
}

internal class UserDataHelperFactoryImpl @Inject constructor() : UserDataHelperFactory {

    override fun create(userData: UserData): UserDataHelper {
        return UserDataHelperImpl(
            userData = userData,
        )
    }
}

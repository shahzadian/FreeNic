package com.shahzadian.user.domain

import com.shahzadian.user.model.UserData
import javax.inject.Inject

interface UserDataHelper {

    fun getSkippedCigsCount(): Int

    fun getSkippedDaysCount(): Int

    fun calculateSkippedDaysForMillis(millis: Long): Int

    fun calculateSavedMoneyForMillis(millis: Long): Float

    fun getSavedMoney(): Float

    fun getSavedMoneyPerDay(): Float
}

internal class UserDataHelperImpl(
    private val userData: UserData
) : UserDataHelper {

    override fun getSkippedCigsCount(): Int {
        return resolveSkippedDays(getTodayMillis()) * userData.cigarettesData.cigarettesPerDay
    }

    override fun getSkippedDaysCount(): Int {
        return resolveSkippedDays(getTodayMillis())
    }

    override fun getSavedMoney(): Float {
        return resolveSavedMoney(getTodayMillis())
    }

    override fun calculateSavedMoneyForMillis(millis: Long): Float {
        return resolveSavedMoney(millis)
    }

    override fun getSavedMoneyPerDay(): Float {
        val packsPerDay = userData.cigarettesData.packsPerDay
        val pricePerPack = userData.cigarettesData.priceData.price / packsPerDay
        return pricePerPack * packsPerDay
    }

    override fun calculateSkippedDaysForMillis(millis: Long): Int {
        return resolveSkippedDays(millis)
    }

    private fun resolveSavedMoney(millis: Long): Float {
        val skippedDaysCount = resolveSkippedDays(millis)
        return skippedDaysCount * getSavedMoneyPerDay()
    }

    private fun resolveSkippedDays(millis: Long): Int {
        val quitMillis = userData.quitData.quitDateMillis
        val delta = millis - quitMillis
        val skippedDays = (delta / (1000 * 60 * 60 * 24)).toInt()
        return skippedDays
    }

    private fun getTodayMillis(): Long = System.currentTimeMillis()
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

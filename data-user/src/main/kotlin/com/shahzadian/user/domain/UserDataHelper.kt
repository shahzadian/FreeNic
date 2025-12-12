package com.shahzadian.user.domain

import com.shahzadian.user.model.UserData
import javax.inject.Inject

interface UserDataHelper {

    fun getSkippedCigsCount(): Int

    fun getSkippedDaysCount(): Int
}

internal class UserDataHelperImpl(
    private val userData: UserData
) : UserDataHelper {

    override fun getSkippedCigsCount(): Int {
        return resolveSkippedDays() * userData.cigarettesData.cigarettesPerDay
    }

    override fun getSkippedDaysCount(): Int {
        return resolveSkippedDays()
    }

    private fun resolveSkippedDays(): Int {
        val quitMillis = userData.quitData.quitDateMillis
        val todayMillis = System.currentTimeMillis()
        val delta = todayMillis - quitMillis
        return (delta / 60 / 60 / 24).toInt()
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

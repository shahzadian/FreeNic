package com.shahzadian.user.data

import com.shahzadian.user.model.UserData

interface UserDataRepository {

    suspend fun isUserRegistered(): Boolean

    suspend fun setUserRegistered(isRegistered: Boolean)

    suspend fun updateQuidDate(quitDateMillis: Long)

    suspend fun getUserData(): UserData?

    suspend fun updateUserData(userData: UserData)
}

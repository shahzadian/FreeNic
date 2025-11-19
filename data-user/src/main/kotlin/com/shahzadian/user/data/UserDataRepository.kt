package com.shahzadian.user.data

import com.shahzadian.user.model.UserData

interface UserDataRepository {

    suspend fun getUserData(): UserData?

    suspend fun updateUserData(userData: UserData)
}

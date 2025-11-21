package com.shahzadian.user.domain.getuserdata

import com.shahzadian.user.model.UserData

interface GetUserDataUseCase {

    suspend fun isUserRegistered(): Boolean

    suspend operator fun invoke(): UserData?
}
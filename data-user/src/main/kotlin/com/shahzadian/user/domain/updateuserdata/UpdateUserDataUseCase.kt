package com.shahzadian.user.domain.updateuserdata

import com.shahzadian.user.model.UserData

interface UpdateUserDataUseCase {

    suspend fun setUserRegistered(isRegistered: Boolean)

    suspend fun updateQuidDate(quitDateMillis: Long)

    suspend operator fun invoke(userData: UserData)
}

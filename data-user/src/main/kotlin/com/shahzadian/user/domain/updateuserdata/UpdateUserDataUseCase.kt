package com.shahzadian.user.domain.updateuserdata

import com.shahzadian.user.model.UserData

interface UpdateUserDataUseCase {

    suspend operator fun invoke(userData: UserData)
}

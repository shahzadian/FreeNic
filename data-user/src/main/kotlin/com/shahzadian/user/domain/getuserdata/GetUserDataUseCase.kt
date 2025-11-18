package com.shahzadian.user.domain.getuserdata

import com.shahzadian.user.model.UserData

interface GetUserDataUseCase {

    suspend operator fun invoke(): UserData?
}
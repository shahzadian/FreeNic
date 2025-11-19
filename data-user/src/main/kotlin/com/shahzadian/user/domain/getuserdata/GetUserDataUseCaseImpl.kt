package com.shahzadian.user.domain.getuserdata

import com.shahzadian.user.data.UserDataRepository
import com.shahzadian.user.model.UserData
import javax.inject.Inject

class GetUserDataUseCaseImpl @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : GetUserDataUseCase {

    override suspend fun invoke(): UserData? {
        return userDataRepository.getUserData()
    }
}

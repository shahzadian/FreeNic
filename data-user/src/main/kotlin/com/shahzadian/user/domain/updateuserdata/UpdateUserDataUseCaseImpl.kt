package com.shahzadian.user.domain.updateuserdata

import com.shahzadian.user.data.UserDataRepository
import com.shahzadian.user.model.UserData
import javax.inject.Inject

class UpdateUserDataUseCaseImpl @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : UpdateUserDataUseCase {

    override suspend fun setUserRegistered(isRegistered: Boolean) {
        userDataRepository.setUserRegistered(isRegistered)
    }

    override suspend fun updateQuidDate(quitDateMillis: Long) {
        userDataRepository.updateQuidDate(quitDateMillis)
    }

    override suspend fun invoke(userData: UserData) {
        userDataRepository.updateUserData(userData)
    }
}

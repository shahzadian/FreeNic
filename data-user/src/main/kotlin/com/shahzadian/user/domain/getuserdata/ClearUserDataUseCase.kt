package com.shahzadian.user.domain.getuserdata

import com.shahzadian.user.data.UserDataRepository
import javax.inject.Inject

interface ClearUserDataUseCase {

    suspend operator fun invoke()
}

internal class ClearUserDataUseCaseImpl @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ClearUserDataUseCase {

    override suspend fun invoke() {
        userDataRepository.clearAllData()
    }
}

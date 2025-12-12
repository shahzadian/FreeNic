package com.shahzadian.achievements.domain

import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import javax.inject.Inject

interface AchievementStateProviderFactory {

    /**
     * @return null if user is not registered
     */
    suspend fun create(): AchievementStateProvider?
}

internal class AchievementStateProviderFactoryImpl @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val userDataHelperFactory: UserDataHelperFactory,
) : AchievementStateProviderFactory {

    override suspend fun create(): AchievementStateProvider? {
        val userData = getUserDataUseCase() ?: return null
        val userDataHelper = userDataHelperFactory.create(userData)
        return AchievementStateProviderImpl(userDataHelper)
    }
}

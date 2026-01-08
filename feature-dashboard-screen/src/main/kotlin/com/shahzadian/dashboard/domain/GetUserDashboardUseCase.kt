package com.shahzadian.dashboard.domain

import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import java.util.Currency
import javax.inject.Inject

internal interface GetUserDashboardUseCase {

    suspend operator fun invoke(): UserDashboard?
}

internal class GetUserDashboardUseCaseImpl @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val userDataHelperFactory: UserDataHelperFactory,
) : GetUserDashboardUseCase {

    override suspend fun invoke(): UserDashboard? {
        val userData = getUserDataUseCase() ?: return null
        val userDataHelper = userDataHelperFactory.create(userData)
        return UserDashboard(
            mainProgressData = MainProgressData(
                noCigDays = userDataHelper.getSkippedDaysCount(),
                savedMoney = userDataHelper.getSavedMoney(),
                currency = try {
                    Currency.getInstance(userData.cigarettesData.priceData.currency).symbol
                } catch (t: Throwable) {
                    null
                },
            ),
        )
    }
}

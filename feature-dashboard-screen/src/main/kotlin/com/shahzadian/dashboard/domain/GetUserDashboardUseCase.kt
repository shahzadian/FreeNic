package com.shahzadian.dashboard.domain

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import java.util.Currency
import javax.inject.Inject

internal interface GetUserDashboardUseCase {

    suspend operator fun invoke(): UserDashboard?
}

// TODO
internal class GetUserDashboardUseCaseImpl @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val userDataHelperFactory: UserDataHelperFactory,
) : GetUserDashboardUseCase {

    override suspend fun invoke(): UserDashboard? {
        val userData = getUserDataUseCase() ?: return null
        val userDataHelper = userDataHelperFactory.create(userData)
        return UserDashboard(
            noCigDays = userDataHelper.getSkippedDaysCount(),
            lungHealthPercents = 40,
            savedMoney = userDataHelper.getSavedMoney(),
            currency = try {
                Currency.getInstance(userData.cigarettesData.priceData.currency)
            } catch (t: Throwable) {
                null
            },
            cigsAvoided = userDataHelper.getSkippedCigsCount(),
            healthProgressItems = listOf(
                HealthProgressItem(
                    label = TextWrapper.FromRes(R.string.dashboard_health_progress_heart_label),
                    icon = ImageWrapper.FromRes(com.shahzadian.dashboard.R.drawable.ic_dashboard_health_progress_heart),
                    percents = 60,
                ),
                HealthProgressItem(
                    label = TextWrapper.FromRes(R.string.dashboard_health_progress_energy_label),
                    icon = ImageWrapper.FromRes(com.shahzadian.dashboard.R.drawable.ic_dashboard_health_progress_energy),
                    percents = 40,
                )
            ),
        )
    }
}

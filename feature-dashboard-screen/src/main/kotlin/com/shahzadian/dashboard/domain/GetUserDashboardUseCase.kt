package com.shahzadian.dashboard.domain

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

internal interface GetUserDashboardUseCase {

    suspend operator fun invoke(): UserDashboard
}

// TODO
internal class GetUserDashboardUseCaseImpl @Inject constructor() : GetUserDashboardUseCase {

    override suspend fun invoke(): UserDashboard {
        return UserDashboard(
            noCigDays = 10,
            lungHealthPercents = 40,
            savedMoney = 150f,
            currency = Currency.getInstance(Locale.US),
            cigsAvoided = 12,
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

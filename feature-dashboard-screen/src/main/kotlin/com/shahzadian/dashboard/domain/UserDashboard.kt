package com.shahzadian.dashboard.domain

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import java.util.Currency

internal class UserDashboard(
    val noCigDays: Int,
    val lungHealthPercents: Int,
    val savedMoney: Float,
    val currency: Currency?,
    val cigsAvoided: Int,
    val healthProgressItems: List<HealthProgressItem>,
)

class HealthProgressItem(
    val label: TextWrapper,
    val icon: ImageWrapper,
    val percents: Int,
)
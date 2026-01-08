package com.shahzadian.dashboard.domain

internal class UserDashboard(
    val mainProgressData: MainProgressData,
)

class MainProgressData(
    val noCigDays: Int,
    val savedMoney: Float,
    val currency: String?,
)
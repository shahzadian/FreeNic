package com.shahzadian.freenic.navigation

import androidx.navigation.NavController
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.navigation.AppNavigator
import javax.inject.Inject

interface AppNavigatorFactory {

    fun create(
        navController: NavController,
        tabsHost: MainTabsHost,
    ): AppNavigator
}

internal class AppNavigatorFactoryImpl @Inject constructor(
    private val analyticsEventSender: AnalyticsEventSender,
) : AppNavigatorFactory {

    override fun create(
        navController: NavController,
        tabsHost: MainTabsHost,
    ): AppNavigator {
        return AppNavigatorImpl(
            navController = navController,
            tabsHost = tabsHost,
            analyticsEventSender = analyticsEventSender,
        )
    }
}

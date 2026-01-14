package com.shahzadian.freenic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppScreen

internal class AppNavigatorImpl(
    private val navController: NavController,
    private val analyticsEventSender: AnalyticsEventSender,
) : AppNavigator {

    override fun navigateToScreen(
        screen: AppScreen,
        keepInStack: Boolean,
        moreActions: NavOptionsBuilder.() -> Unit
    ) {
        navigateToRoute(
            route = screen.route,
            keepInStack = keepInStack,
            moreActions = moreActions,
        )
    }

    override fun navigateBack() {
        navController.popBackStack()
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun navigateToRoute(
        route: String,
        keepInStack: Boolean,
        moreActions: NavOptionsBuilder.() -> Unit
    ) {
        navController.navigate(route) {
            if (!keepInStack) {
                popUpTo(route) {
                    inclusive = true
                }
            }
            moreActions()
        }
        trackScreenView(route)
    }

    private fun trackScreenView(screenName: String) {
        analyticsEventSender.send(
            AnalyticsEvent(
                name = AnalyticsEvent.EventNames.SCREEN_VIEW,
                params = mapOf(AnalyticsEvent.EventParams.SCREEN_NAME to screenName)
            )
        )
    }

    @Composable
    override fun currentRoute(): String? {
        val backStackEntry by navController.currentBackStackEntryAsState()
        return backStackEntry?.destination?.route
    }
}

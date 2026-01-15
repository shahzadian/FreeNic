package com.shahzadian.freenic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
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
        navController.currentBackStack.value.lastOrNull()?.destination?.route?.let {
            trackScreenView(
                screenName = it,
                isBack = true,
            )
        }
    }

    override fun navigateUp() {
        navController.navigateUp()
        navController.currentBackStack.value.lastOrNull()?.destination?.route?.let {
            trackScreenView(
                screenName = it,
                isBack = true,
            )
        }
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

    private fun trackScreenView(
        screenName: String,
        isBack: Boolean = false
    ) {
        analyticsEventSender.send(
            AnalyticsEvent(
                name = AnalyticsEventConst.SCREEN_VIEW,
                params = mapOf(
                    AnalyticsEventConst.SCREEN_VIEW_PARAM_SCREEN_NAME to screenName.asAnalyticsProperty(),
                    AnalyticsEventConst.SCREEN_VIEW_PARAM_NAV_IS_BACK to isBack.asAnalyticsProperty(),
                )
            )
        )
    }

    @Composable
    override fun currentRoute(): String? {
        val backStackEntry by navController.currentBackStackEntryAsState()
        return backStackEntry?.destination?.route
    }
}

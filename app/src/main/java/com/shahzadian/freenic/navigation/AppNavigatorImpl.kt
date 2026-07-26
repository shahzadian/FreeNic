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

/**
 * Navigates over two controllers:
 * - [navController] hosts the full screen destinations and the tab container ([MAIN_TABS_ROUTE]),
 * - the controller inside [MainTabsHost] hosts the bottom bar destinations.
 *
 * Callers keep using a single [AppNavigator]; the target route decides which controller is used.
 */
internal class AppNavigatorImpl(
    private val navController: NavController,
    private val tabsHost: MainTabsHost,
    private val analyticsEventSender: AnalyticsEventSender,
) : AppNavigator {

    private val tabRoutes: List<String> = AppScreen
        .getAll()
        .filter { it is AppScreen.NavBarScreen }
        .map { it.route }

    private val isTabContainerOnScreen: Boolean
        get() = navController.currentDestination?.route == MAIN_TABS_ROUTE

    /**
     * Controller owning the destination the user currently sees.
     */
    private val currentController: NavController
        get() = if (isTabContainerOnScreen) tabsHost.navController ?: navController else navController

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
        val controller = currentController
        controller.popBackStack()
        controller.currentBackStack.value.lastOrNull()?.destination?.route?.let {
            trackScreenView(
                screenName = it,
                isBack = true,
            )
        }
    }

    override fun navigateUp() {
        val controller = currentController
        controller.navigateUp()
        controller.currentBackStack.value.lastOrNull()?.destination?.route?.let {
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
        if (route in tabRoutes) {
            navigateToTab(
                route = route,
                keepInStack = keepInStack,
                moreActions = moreActions,
            )
        } else {
            // Full screen destinations always live above the tab container.
            navController.navigate(route) {
                if (!keepInStack) {
                    popUpTo(route) {
                        inclusive = true
                    }
                }
                moreActions()
            }
        }
        trackScreenView(route)
    }

    private fun navigateToTab(
        route: String,
        keepInStack: Boolean,
        moreActions: NavOptionsBuilder.() -> Unit
    ) {
        if (isTabContainerOnScreen) {
            val tabsNavController = tabsHost.navController
            if (tabsNavController == null) {
                // The container is the current destination but its NavHost is not composed yet.
                tabsHost.pendingTabRoute = route
                return
            }
            tabsNavController.navigate(route) {
                if (!keepInStack) {
                    popUpTo(route) {
                        inclusive = true
                    }
                }
                moreActions()
            }
            return
        }

        // The container is not on screen: remember the tab, then bring the container back.
        tabsHost.pendingTabRoute = route
        val returnedToContainer = navController.popBackStack(MAIN_TABS_ROUTE, false)
        if (returnedToContainer) return

        navController.navigate(MAIN_TABS_ROUTE) {
            if (!keepInStack) {
                popUpTo(MAIN_TABS_ROUTE) {
                    inclusive = true
                }
            }
            moreActions()
        }
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
        val currentRoute = backStackEntry?.destination?.route
        val tabsNavController = tabsHost.navController
        if (currentRoute != MAIN_TABS_ROUTE || tabsNavController == null) {
            return currentRoute
        }
        val tabBackStackEntry by tabsNavController.currentBackStackEntryAsState()
        return tabBackStackEntry?.destination?.route
    }
}

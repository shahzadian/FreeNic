package com.shahzadian.freenic.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppScreen
import com.shahzadian.dashboard.ui.DashboardScreen
import com.shahzadian.goalsscreen.ui.GoalsScreen
import com.shahzadian.insightsscreen.ui.InsightsScreen
import com.shahzadian.mainscreen.ui.MainBottomBar
import com.shahzadian.mainscreen.ui.MainScreen
import com.shahzadian.profilescreen.ui.ProfileScreen

/**
 * Container of every [AppScreen.NavBarScreen] destination.
 *
 * The bottom bar belongs to this container instead of the whole app, so it is never hidden: opening
 * a screen without a bottom bar pushes an outer destination on top of the container, and the bar
 * simply leaves together with the tab it belongs to.
 */
@Composable
fun MainTabsScreen(
    tabsHost: MainTabsHost,
    navigator: AppNavigator,
) {
    val tabsNavController = rememberNavController()

    // Published during composition so the bottom bar resolves the selected tab on its first frame.
    remember(tabsNavController) {
        tabsHost.navController = tabsNavController
    }
    DisposableEffect(tabsNavController) {
        onDispose {
            if (tabsHost.navController === tabsNavController) {
                tabsHost.navController = null
            }
        }
    }

    // Applies a tab requested while this NavHost was not composed yet.
    val pendingTabRoute = tabsHost.pendingTabRoute
    LaunchedEffect(tabsNavController, pendingTabRoute) {
        if (pendingTabRoute == null) return@LaunchedEffect
        tabsHost.pendingTabRoute = null
        if (pendingTabRoute != tabsNavController.currentDestination?.route) {
            tabsNavController.navigate(pendingTabRoute) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Scaffold(
        bottomBar = {
            MainBottomBar(
                navigator = navigator,
            )
        },
        // System insets are already consumed by the outer Scaffold in AppNavigation.
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        NavHost(
            navController = tabsNavController,
            startDestination = AppScreen.MainScreen.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(
                route = AppScreen.MainScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                MainScreen(
                    navigator = navigator,
                )
            }
            composable(
                route = AppScreen.DashboardScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                DashboardScreen(
                    navigator = navigator,
                    modifier = BaseModifier,
                )
            }
            composable(
                route = AppScreen.InsightsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                InsightsScreen(
                    navigator = navigator,
                    modifier = BaseModifier,
                )
            }
            composable(
                route = AppScreen.GoalsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                GoalsScreen(
                    navigator = navigator,
                    modifier = BaseModifier,
                )
            }
            composable(
                route = AppScreen.ProfileScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                ProfileScreen(
                    navigator = navigator,
                    modifier = BaseModifier,
                )
            }
        }
    }
}

package com.shahzadian.freenic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppScreen

internal class AppNavigatorImpl(
    private val navController: NavController,
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
    }

    @Composable
    override fun currentRoute(): String? {
        val backStackEntry by navController.currentBackStackEntryAsState()
        return backStackEntry?.destination?.route
    }
}

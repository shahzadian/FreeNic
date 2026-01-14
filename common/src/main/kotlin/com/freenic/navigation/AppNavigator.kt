package com.freenic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavOptionsBuilder
import com.freenic.ui.AppScreen

interface AppNavigator {

    fun navigateToScreen(
        screen: AppScreen,
        keepInStack: Boolean = true,
        moreActions: NavOptionsBuilder.() -> Unit = {},
    )

    fun navigateBack()

    /**
     * Navigates up in the navigation hierarchy (similar to NavController.navigateUp()).
     */
    fun navigateUp()

    /**
     * Navigate directly to any route string. Useful for routes constructed with parameters.
     */
    fun navigateToRoute(
        route: String,
        keepInStack: Boolean = true,
        moreActions: NavOptionsBuilder.() -> Unit = {},
    )

    @Composable
    fun currentRoute(): String?
}

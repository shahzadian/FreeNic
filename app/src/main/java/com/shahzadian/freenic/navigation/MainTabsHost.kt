package com.shahzadian.freenic.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

/**
 * Route of the outer destination that hosts the bottom bar and the tab [androidx.navigation.compose.NavHost].
 */
const val MAIN_TABS_ROUTE: String = "main_tabs_graph"

/**
 * Bridge between [AppNavigatorImpl] and the tab [androidx.navigation.compose.NavHost] created inside
 * [MainTabsScreen].
 *
 * The tab controller only exists while the tab container is composed, so navigation that targets a
 * tab from the outside stores the request in [pendingTabRoute] and lets the container apply it once
 * it is on screen again.
 */
class MainTabsHost {

    /**
     * Controller of the tab NavHost. Assigned while [MainTabsScreen] is composed, `null` otherwise.
     */
    var navController: NavController? = null

    /**
     * Tab requested while the tab NavHost was not composed yet. Consumed once by [MainTabsScreen],
     * which observes it so the request is applied whether it arrives before or after the container
     * enters composition.
     */
    var pendingTabRoute: String? by mutableStateOf(null)
}

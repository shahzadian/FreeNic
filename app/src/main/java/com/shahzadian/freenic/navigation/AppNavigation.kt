package com.shahzadian.freenic.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freenic.ui.AppScreen
import com.shahzadian.breatheexercisescreen.ui.BreatheExerciseScreen
import com.shahzadian.cravingsscreen.ui.CravingsScreen
import com.shahzadian.dashboard.ui.DashboardScreen
import com.shahzadian.datafill.ui.DataFillScreen
import com.shahzadian.distractionactivityscreen.guessshape.ui.GuessShapeScreen
import com.shahzadian.distractionactivityscreen.ui.DistractionActivityScreen
import com.shahzadian.insightsscreen.ui.InsightsScreen
import com.shahzadian.mainscreen.ui.MainBottomBar
import com.shahzadian.mainscreen.ui.MainScreen
import com.shahzadian.profilescreen.ui.ProfileScreen
import com.shahzadian.welcomescreen.ui.onboardingscreen.OnboardingScreen
import com.shahzadian.welcomescreen.ui.welcomescreen.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Keep explicit remembered state for bottom bar visibility to avoid timing issues
    val showBottomBarState = remember { mutableStateOf(false) }

    val bottomBarRoutes = remember {
        AppScreen
            .getAll()
            .filterNot { it is AppScreen.NoNavBarScreen }
            .map { it.route }
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            val shouldShow = destination.hierarchy.any { it.route in bottomBarRoutes }
            showBottomBarState.value = shouldShow
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBarState.value) {
                MainBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.WelcomeScreen.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = AppScreen.WelcomeScreen.route) {
                WelcomeScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.OnboardingScreen.route) {
                OnboardingScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.DataFillScreen.route) {
                DataFillScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }
            composable(
                route = AppScreen.MainScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                MainScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }
            composable(
                route = AppScreen.DashboardScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                DashboardScreen(
                    modifier = Modifier
                )
            }

//            Cravings screen
            composable(
                route = AppScreen.CravingsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                CravingsScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }
            composable(
                route = AppScreen.BreatheExerciseScreen.route,
                exitTransition = { ExitTransition.None },
            ) {
                BreatheExerciseScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }
            composable(
                route = AppScreen.DistractionActivityScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                DistractionActivityScreen(
                    navController = navController,
                )
            }
            composable(
                route = AppScreen.GuessShapeScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                GuessShapeScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }

            composable(
                route = AppScreen.InsightsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                InsightsScreen()
            }

            composable(
                route = AppScreen.ProfileScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                ProfileScreen()
            }
        }
    }
}

package com.shahzadian.freenic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freenic.ui.AppScreen
import com.shahzadian.homescreen.ui.homescreen.HomeScreen
import com.shahzadian.homescreen.ui.WelcomeScreen
import com.shahzadian.homescreen.ui.onboardingscreen.OnboardingScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.HomeScreen.route) {
        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = AppScreen.WelcomeScreen.route) {
            WelcomeScreen()
        }
        composable(route = AppScreen.OnboardingScreen.route) {
            OnboardingScreen()
        }
        // TODO: Add other screens
    }
}

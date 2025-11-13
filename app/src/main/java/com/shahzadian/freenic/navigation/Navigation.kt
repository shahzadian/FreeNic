package com.shahzadian.freenic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freenic.ui.AppScreen
import com.shahzadian.datafill.startscreen.PersonalDataFillStartScreen
import com.shahzadian.homescreen.ui.homescreen.HomeScreen
import com.shahzadian.homescreen.ui.onboardingscreen.OnboardingScreen
import com.shahzadian.homescreen.ui.welcomescreen.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.HomeScreen.route) {
        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = AppScreen.WelcomeScreen.route) {
            WelcomeScreen(
                navController = navController,
            )
        }
        composable(route = AppScreen.OnboardingScreen.route) {
            OnboardingScreen(
                navController = navController,
            )
        }
        composable(route = AppScreen.PersonalDataFillStartScreen.route) {
            PersonalDataFillStartScreen(
            )
        }
    }
}

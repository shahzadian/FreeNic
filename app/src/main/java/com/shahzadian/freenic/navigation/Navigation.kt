package com.shahzadian.freenic.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freenic.ui.AppScreen
import com.shahzadian.dashboard.ui.DashboardScreen
import com.shahzadian.datafill.ui.DataFillScreen
import com.shahzadian.welcomescreen.ui.onboardingscreen.OnboardingScreen
import com.shahzadian.welcomescreen.ui.welcomescreen.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.WelcomeScreen.route) {
        composable(route = AppScreen.WelcomeScreen.route) {
            Scaffold {
                WelcomeScreen(
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }
        }
        composable(route = AppScreen.OnboardingScreen.route) {
            Scaffold {
                OnboardingScreen(
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }
        }
        composable(route = AppScreen.DataFillScreen.route) {
            Scaffold {
                DataFillScreen(
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }
        }
        composable(route = AppScreen.DashboardScreen.route) {
            Scaffold {
                DashboardScreen(
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}

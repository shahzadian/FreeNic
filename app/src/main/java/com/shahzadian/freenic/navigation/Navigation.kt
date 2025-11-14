package com.shahzadian.freenic.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freenic.ui.AppScreen
import com.shahzadian.datafill.ui.DataFillScreen
import com.shahzadian.homescreen.ui.homescreen.HomeScreen
import com.shahzadian.homescreen.ui.onboardingscreen.OnboardingScreen
import com.shahzadian.homescreen.ui.welcomescreen.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.HomeScreen.route) {
        composable(route = AppScreen.HomeScreen.route) {
            Scaffold {
                HomeScreen(
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }
        }
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
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}

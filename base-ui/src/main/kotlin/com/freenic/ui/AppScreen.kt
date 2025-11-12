package com.freenic.ui

sealed class AppScreen(val route: String) {
    object HomeScreen : AppScreen("home_screen")
    object WelcomeScreen : AppScreen("welcome_screen")
    object OnboardingScreen : AppScreen("onboarding_screen")
}

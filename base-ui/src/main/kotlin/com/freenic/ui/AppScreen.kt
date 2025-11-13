package com.freenic.ui

sealed class AppScreen(val route: String) {

//    Home
    object HomeScreen : AppScreen("home_screen")
    object WelcomeScreen : AppScreen("welcome_screen")
    object OnboardingScreen : AppScreen("onboarding_screen")

//    Personal data fill
    object PersonalDataFillStartScreen : AppScreen("personal_data_fill_start_screen")
}

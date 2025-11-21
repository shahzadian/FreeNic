package com.freenic.ui

sealed class AppScreen {

    abstract val route: String

    object WelcomeScreen : AppScreen() {
        override val route = "welcome_screen"
    }

    object OnboardingScreen : AppScreen() {
        override val route = "onboarding_screen"
    }

    //    Personal data fill
    object DataFillScreen : AppScreen() {
        override val route: String = "data_fill_screen"
    }

    //    Main screen with nav bar
    object MainScreen : AppScreen(), NavBarScreen {

        override val route: String = "main_screen"
    }

    // User data dashboard
    object DashboardScreen : AppScreen(), NavBarScreen {

        override val route: String = "dashboard_screen"
    }

    companion object {
        fun getAll(): List<AppScreen> {
            return listOf(
                WelcomeScreen,
                OnboardingScreen,
                DataFillScreen,
                MainScreen,
                DashboardScreen,
            )
        }
    }

    interface NavBarScreen
}

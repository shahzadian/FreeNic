package com.freenic.ui

sealed class AppScreen {

    abstract val route: String

    open fun shouldShowWithPremiumIcon(): Boolean = false

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

    object MainScreen : AppScreen(), NavBarScreen {

        override val route: String = "main_screen"
    }

    object DashboardScreen : AppScreen(), NavBarScreen {

        override val route: String = "dashboard_screen"
    }

    object CravingsScreen : AppScreen(), NavBarScreen {
        override val route: String = "cravings_screen"
    }

    object BreatheExerciseScreen : AppScreen() {
        override val route: String = "breathe_exercise_screen"
    }

    object DistractionActivityScreen : AppScreen() {
        override val route: String = "distraction_activity_screen"
    }

    object GuessShapeScreen : AppScreen() {
        override val route: String = "guess_shape_screen"
    }

    object ProfileScreen : AppScreen(), NavBarScreen {
        override val route: String = "profile_screen"
    }

    object PaywallScreen : AppScreen() {
        override val route: String = "paywall_screen"
    }

    object GoalsScreen : AppScreen(), NavBarScreen {
        override val route: String = "goals_screen"

        override fun shouldShowWithPremiumIcon(): Boolean {
            return true
        }
    }

    companion object {
        fun getAll(): List<AppScreen> {
            return listOf(
                WelcomeScreen,
                OnboardingScreen,
                DataFillScreen,
                MainScreen,
                DashboardScreen,
                CravingsScreen,
                BreatheExerciseScreen,
                GuessShapeScreen,
                ProfileScreen,
                PaywallScreen,
                GoalsScreen,
            )
        }
    }

    interface NavBarScreen
}

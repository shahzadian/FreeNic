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

    object DashboardScreen : AppScreen(), NavBarScreen {

        override val route: String = "dashboard_screen"
    }

    object CravingsScreen : AppScreen(), NavBarScreen {
        override val route: String = "cravings_screen"
    }

    object BreatheExerciseScreen : AppScreen(), NavBarScreen {
        override val route: String = "breathe_exercise_screen"
    }

    object DistractionActivityScreen : AppScreen(), NavBarScreen {
        override val route: String = "distraction_activity_screen"
    }

    object GuessShapeScreen : AppScreen(), NavBarScreen {
        override val route: String = "guess_shape_screen"
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
            )
        }
    }

    interface NavBarScreen
}

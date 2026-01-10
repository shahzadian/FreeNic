package com.freenic.ui

sealed class AppScreen {

    abstract val route: String

    /**
     * Route without arguments
     */
    open fun getBaseRoute(): String {
        return route
    }

    open fun shouldShowWithPremiumIcon(): Boolean = false

    object WelcomeScreen : AppScreen() {

        override val route: String = "$BASE_ROUTE?${ARG_TARGET_SCREEN}={${ARG_TARGET_SCREEN}}"

        fun composeRoute(targetScreen: String?): String {
            return targetScreen?.let { "$BASE_ROUTE?${ARG_TARGET_SCREEN}=$it" } ?: BASE_ROUTE
        }

        override fun getBaseRoute(): String {
            return BASE_ROUTE
        }

        const val ARG_TARGET_SCREEN: String = "target_screen"
        private const val BASE_ROUTE: String = "welcome_screen"
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

    object SavingsGoalCreateScreen : AppScreen() {
        override val route: String = "$BASE_ROUTE?${ARG_IS_EDIT}={${ARG_IS_EDIT}}"

        fun composeRoute(isEdit: Boolean = false): String {
            return "$BASE_ROUTE?${ARG_IS_EDIT}=$isEdit"
        }

        override fun getBaseRoute(): String {
            return BASE_ROUTE
        }

        const val ARG_IS_EDIT: String = "is_edit"
        private const val BASE_ROUTE: String = "savings_goal_screen"
    }

    object SavingsGoalDetailsScreen : AppScreen() {
        override val route: String = "savings_goal_details_screen"
    }

    object SavingsGoalShareScreen : AppScreen() {
        override val route: String = "savings_goal_share_screen"
    }

    object AchievementsScreen : AppScreen() {
        override val route: String = "achievements_screen"
    }

    object AchievementShareScreen : AppScreen() {

        override val route: String = "$BASE_ROUTE/{$ARG_ACHIEVEMENT_ID}"

        fun composeRoute(achievementId: String): String {
            return "${BASE_ROUTE}/$achievementId"
        }

        override fun getBaseRoute(): String {
            return BASE_ROUTE
        }

        const val ARG_ACHIEVEMENT_ID = "achievement_id"
        private const val BASE_ROUTE: String = "achievement_share_screen"
    }

    object InsightsScreen : AppScreen() {
        override val route: String = "insights_screen"
    }

    object DebugPanelScreen : AppScreen() {
        override val route: String = "debug_panel_screen"
    }

    object MotivationalNotesScreen : AppScreen() {
        override val route: String = "motivational_notes_screen"
    }

    object SlipConfirmationScreen : AppScreen() {
        override val route: String = "slide_confirmation_screen"
    }

    object SlipReasonScreen : AppScreen() {
        override val route: String = "slide_reason_screen"
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
                SavingsGoalCreateScreen,
                SavingsGoalDetailsScreen,
                SavingsGoalShareScreen,
                AchievementsScreen,
                AchievementShareScreen,
                InsightsScreen,
                DebugPanelScreen,
                MotivationalNotesScreen,
                SlipConfirmationScreen,
                SlipReasonScreen,
            )
        }
    }

    interface NavBarScreen
}

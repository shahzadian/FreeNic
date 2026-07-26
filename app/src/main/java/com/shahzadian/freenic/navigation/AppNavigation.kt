package com.shahzadian.freenic.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.freenic.ui.AppScreen
import com.shahzadian.breatheexercisescreen.ui.BreatheExerciseScreen
import com.shahzadian.cravingsscreen.ui.CravingsScreen
import com.shahzadian.cravingsupportscreen.ui.CravingSupportScreen
import com.shahzadian.datafill.ui.DataFillScreen
import com.shahzadian.debugpanelscreen.ui.DebugPanelScreen
import com.shahzadian.distractionactivityscreen.guessshape.ui.GuessShapeScreen
import com.shahzadian.distractionactivityscreen.ui.DistractionActivityScreen
import com.shahzadian.goalsscreen.ui.achievementsharescreen.AchievementShareScreen
import com.shahzadian.goalsscreen.ui.achievementsscreen.AchievementsScreen
import com.shahzadian.journeycalendarscreen.ui.JourneyCalendarScreen
import com.shahzadian.motivationalnotesscreen.ui.MotivationalNotesScreen
import com.shahzadian.profilescreen.managesubscription.ManageSubscriptionScreen
import com.shahzadian.savingsgoalscreen.ui.create.SavingsGoalCreateScreen
import com.shahzadian.savingsgoalscreen.ui.details.SavingsGoalDetailsScreen
import com.shahzadian.savingsgoalscreen.ui.share.SavingsGoalShareScreen
import com.shahzadian.slipscreen.ui.slipconfirmation.SlipConfirmationScreen
import com.shahzadian.slipscreen.ui.slipreason.ui.SlipReasonScreen
import com.shahzadian.slipscreen.ui.slipresult.SlipResultScreen
import com.shahzadian.subscription.paywall.ui.PaywallScreen
import com.shahzadian.welcomescreen.ui.onboardingscreen.OnboardingScreen
import com.shahzadian.welcomescreen.ui.welcomescreen.WelcomeScreen

@Composable
fun AppNavigation(
    targetScreen: String?,
    targetScreenSource: String?,
    appNavigatorFactory: AppNavigatorFactory,
) {
    val navController = rememberNavController()
    val tabsHost = remember { MainTabsHost() }

    val appNavigator = remember {
        appNavigatorFactory.create(
            navController = navController,
            tabsHost = tabsHost,
        )
    }

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.WelcomeScreen.composeRoute(
                targetScreen = targetScreen,
                targetScreenSource = targetScreenSource,
            ),
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(
                route = AppScreen.WelcomeScreen.route,
                arguments = listOf(
                    navArgument(AppScreen.WelcomeScreen.ARG_TARGET_SCREEN) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument(AppScreen.WelcomeScreen.ARG_TARGET_SCREEN_SOURCE) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) {
                WelcomeScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }
            composable(route = AppScreen.OnboardingScreen.route) {
                OnboardingScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }
            composable(route = AppScreen.DataFillScreen.route) {
                DataFillScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }
//            Bottom bar destinations live in their own NavHost inside this container
            composable(
                route = MAIN_TABS_ROUTE,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                MainTabsScreen(
                    tabsHost = tabsHost,
                    navigator = appNavigator,
                )
            }

//            Cravings screen
            composable(
                route = AppScreen.CravingsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                CravingsScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }
            composable(
                route = AppScreen.BreatheExerciseScreen.route,
                exitTransition = { ExitTransition.None },
            ) {
                BreatheExerciseScreen(
                    navigator = appNavigator,
                )
            }
            composable(
                route = AppScreen.DistractionActivityScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                DistractionActivityScreen(
                    navigator = appNavigator,
                )
            }
            composable(
                route = AppScreen.GuessShapeScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                GuessShapeScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.PaywallScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                arguments = listOf(
                    navArgument(AppScreen.PaywallScreen.ARG_SOURCE) {
                        type = NavType.StringType
                    }
                )
            ) {
                PaywallScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.SavingsGoalCreateScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                arguments = listOf(
                    navArgument(AppScreen.SavingsGoalCreateScreen.ARG_IS_EDIT) {
                        type = NavType.BoolType
                        defaultValue = false
                        nullable = false
                    }
                )
            ) {
                SavingsGoalCreateScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.SavingsGoalDetailsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                SavingsGoalDetailsScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.SavingsGoalShareScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                SavingsGoalShareScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.AchievementsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                AchievementsScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.AchievementShareScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                arguments = listOf(
                    navArgument(AppScreen.AchievementShareScreen.ARG_ACHIEVEMENT_ID) {
                        type = NavType.StringType
                    }
                )
            ) { _ ->
                AchievementShareScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.DebugPanelScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                DebugPanelScreen(
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.MotivationalNotesScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                MotivationalNotesScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.SlipConfirmationScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                SlipConfirmationScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.SlipReasonScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                SlipReasonScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.SlipResultScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                SlipResultScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.JourneyCalendarScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                JourneyCalendarScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.ManageSubscriptionScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                ManageSubscriptionScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.CravingSupportScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                CravingSupportScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }
        }
    }
}

internal val BaseModifier: Modifier
    @Composable
    get() = Modifier.background(MaterialTheme.colorScheme.background)

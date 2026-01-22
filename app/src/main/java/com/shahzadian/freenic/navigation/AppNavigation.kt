package com.shahzadian.freenic.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.freenic.ui.AppScreen
import com.shahzadian.breatheexercisescreen.ui.BreatheExerciseScreen
import com.shahzadian.cravingsscreen.ui.CravingsScreen
import com.shahzadian.dashboard.ui.DashboardScreen
import com.shahzadian.datafill.ui.DataFillScreen
import com.shahzadian.debugpanelscreen.ui.DebugPanelScreen
import com.shahzadian.distractionactivityscreen.guessshape.ui.GuessShapeScreen
import com.shahzadian.distractionactivityscreen.ui.DistractionActivityScreen
import com.shahzadian.goalsscreen.ui.GoalsScreen
import com.shahzadian.goalsscreen.ui.achievementsharescreen.AchievementShareScreen
import com.shahzadian.goalsscreen.ui.achievementsscreen.AchievementsScreen
import com.shahzadian.insightsscreen.ui.InsightsScreen
import com.shahzadian.journeycalendarscreen.ui.JourneyCalendarScreen
import com.shahzadian.mainscreen.ui.MainBottomBar
import com.shahzadian.mainscreen.ui.MainScreen
import com.shahzadian.motivationalnotesscreen.ui.MotivationalNotesScreen
import com.shahzadian.profilescreen.managesubscription.ManageSubscriptionScreen
import com.shahzadian.profilescreen.ui.ProfileScreen
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
    appNavigatorFactory: AppNavigatorFactory,
) {
    val navController = rememberNavController()
    // Keep explicit remembered state for bottom bar visibility to avoid timing issues
    val showBottomBarState = remember { mutableStateOf(false) }

    val appNavigator = remember {
        appNavigatorFactory.create(
            navController = navController,
        )
    }

    val bottomBarRoutes = remember {
        AppScreen
            .getAll()
            .filter { it is AppScreen.NavBarScreen }
            .map { it.route }
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            val shouldShow = destination.hierarchy.any { it.route in bottomBarRoutes }
            showBottomBarState.value = shouldShow
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBarState.value,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 220,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        slideInVertically(
                            animationSpec = tween(
                                durationMillis = 220,
                                easing = FastOutSlowInEasing
                            ),
                            initialOffsetY = { it / 3 } // slight slide from below
                        ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 180,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        slideOutVertically(
                            animationSpec = tween(
                                durationMillis = 180,
                                easing = FastOutSlowInEasing
                            ),
                            targetOffsetY = { it / 3 }
                        )
            ) {
                MainBottomBar(
                    navigator = appNavigator,
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.WelcomeScreen.composeRoute(
                targetScreen = targetScreen
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
            composable(
                route = AppScreen.MainScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                MainScreen(
                    navigator = appNavigator,
                )
            }
            composable(
                route = AppScreen.DashboardScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                DashboardScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
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
                route = AppScreen.ProfileScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                ProfileScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.PaywallScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                PaywallScreen(
                    navigator = appNavigator,
                    modifier = BaseModifier,
                )
            }

            composable(
                route = AppScreen.GoalsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                GoalsScreen(
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
                route = AppScreen.InsightsScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) { _ ->
                InsightsScreen(
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
        }
    }
}

private val BaseModifier: Modifier
    @Composable
    get() = Modifier.background(MaterialTheme.colorScheme.background)

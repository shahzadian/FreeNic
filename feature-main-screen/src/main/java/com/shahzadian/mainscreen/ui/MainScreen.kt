package com.shahzadian.mainscreen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppScreen

@Composable
fun MainScreen(
    navigator: AppNavigator,
) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    // Redirect to the first bottom tab when MainScreen is opened
    LaunchedEffect(state) {
        val pages = (state as? MainViewState.Default)?.pages
        val firstRoute = pages?.firstOrNull()?.screen
        if (firstRoute != null) {
            navigator.navigateToScreen(screen = firstRoute) {
                launchSingleTop = true
                restoreState = true
                popUpTo(AppScreen.MainScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}

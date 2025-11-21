package com.shahzadian.mainscreen.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppScreen

@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    // Redirect to the first bottom tab when MainScreen is opened
    LaunchedEffect(state) {
        val pages = (state as? MainViewState.Default)?.pages
        val firstRoute = pages?.firstOrNull()?.route
        if (firstRoute != null) {
            navController.navigate(firstRoute) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

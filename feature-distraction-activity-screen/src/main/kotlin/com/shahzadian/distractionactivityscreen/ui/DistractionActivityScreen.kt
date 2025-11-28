package com.shahzadian.distractionactivityscreen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppScreen
import com.shahzadian.distractionactivityscreen.ui.viewmodel.DistractionActivityViewModel
import com.shahzadian.distractionactivityscreen.ui.viewmodel.DistractionActivityViewState

@Composable
fun DistractionActivityScreen(
    navController: NavController,
) {
    val viewModel: DistractionActivityViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is DistractionActivityViewState.Loading -> {}
        is DistractionActivityViewState.Data -> {
            val activity = (state as DistractionActivityViewState.Data).activityType.appScreen
            navController.navigate(activity.route) {
                popUpTo(AppScreen.DistractionActivityScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}

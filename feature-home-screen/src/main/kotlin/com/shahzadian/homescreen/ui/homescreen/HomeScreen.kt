package com.shahzadian.homescreen.ui.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.freenic.ui.AppScreen
import com.shahzadian.homescreen.ui.homescreen.HomeScreenViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(),
) {
    val state by viewModel.viewStateFlow.collectAsState()

    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel.viewCommandFlow.collect { command ->
            when (command) {
                is HomeScreenCommand.Navigate -> {
                    navController.navigate(
                        route = command.screenId,
                    ) {
                        if (command.shouldRemoveCurrentScreenFromStack) {
                            popUpTo(AppScreen.HomeScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}

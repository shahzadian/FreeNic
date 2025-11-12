package com.shahzadian.homescreen.ui.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.freenic.ui.AppScreen

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel.viewCommandFlow.collect { command ->
            when (command) {
                is HomeViewCommand.Navigate -> {
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

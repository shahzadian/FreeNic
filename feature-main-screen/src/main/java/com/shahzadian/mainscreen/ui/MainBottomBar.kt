package com.shahzadian.mainscreen.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper

@Composable
fun MainBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    if (state is MainViewState.Default) {
        val pages = (state as MainViewState.Default).pages
        NavigationBar(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            pages.forEach { item ->
                val isSelected = currentRoute == item.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = imageFromWrapper(item.icon),
                            contentDescription = textFromWrapper(item.title),
                        )
                    },
                    label = {
                        Text(
                            text = textFromWrapper(item.title),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                )
            }
        }
    }
}

package com.shahzadian.mainscreen.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.mainscreen.ui.model.MainScreenItemUiModel

@Composable
fun MainBottomBar(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()

    if (state is MainViewState.Default) {
        val pages = (state as MainViewState.Default).pages
        Column(
            modifier = modifier,
        ) {
            HorizontalDivider(
                modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outline),
                thickness = 1.dp,
            )
            NavBarView(
                pages = pages,
                navigator = navigator,
            )
        }
    }
}

@Composable
private fun NavBarView(
    pages: List<MainScreenItemUiModel>,
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navigator.currentRoute()
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        // System insets are already applied by the Scaffold hosting this bar
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        pages.forEach { item ->
            val isSelected = currentRoute == item.screen.route
            NavigationBarItem(
                selected = isSelected,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    unselectedTextColor = MaterialTheme.colorScheme.outline,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent,
                ),
                onClick = {
                    if (!isSelected) {
                        navigator.navigateToScreen(item.screen) {
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
                        modifier = Modifier.padding(end = 2.dp),
                    )
                }
            )
        }
    }
}
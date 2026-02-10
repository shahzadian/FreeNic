package com.shahzadian.goalsscreen.ui.achievementsscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppLoaderView
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.MainToolbarView
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.goalsscreen.ui.AchievementsBlockView
import com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel.AchievementsViewCommand
import com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel.AchievementsViewEvent
import com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel.AchievementsViewModel
import com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel.AchievementsViewState

@Composable
fun AchievementsScreen(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    val viewModel: AchievementsViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is AchievementsViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is AchievementsViewState.Data -> {
            ContentView(
                data = (state as AchievementsViewState.Data),
                onEvent = viewModel::onViewEvent,
                modifier = modifier,
            )
        }
    }

    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is AchievementsViewCommand.NavigateToAchievementShareScreen -> {
                        navigator.navigateToRoute(AppScreen.AchievementShareScreen.composeRoute(command.id))
                    }
                    is AchievementsViewCommand.NavigateBack -> {
                        navigator.navigateBack()
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: AchievementsViewState.Data,
    onEvent: (AchievementsViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onClick: (String) -> Unit = remember {
        { onEvent(AchievementsViewEvent.OnAchievementClicked(it)) }
    }
    val onShareClick: (String) -> Unit = remember {
        { onEvent(AchievementsViewEvent.OnAchievementShareClicked(it)) }
    }
    val onBackIconClick: () -> Unit = remember {
        { onEvent(AchievementsViewEvent.OnBackIconClicked) }
    }
    Scaffold(
        topBar = {
            MainToolbarView(
                title = TextWrapper.FromRes(R.string.achievements_header_title),
                hasBackIcon = true,
                onBackIconClicked = onBackIconClick,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
        },
        modifier = modifier.padding(16.dp)
    ) {
        AchievementsBlockView(
            data = data.achievementsData,
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            onAchievementClicked = onClick,
            onAchievementShareClicked = onShareClick,
        )
    }
}

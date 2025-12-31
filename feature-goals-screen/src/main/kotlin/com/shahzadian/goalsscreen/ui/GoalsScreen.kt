package com.shahzadian.goalsscreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.goalsscreen.ui.savingsgoal.SavingsGoalViewExisting
import com.shahzadian.goalsscreen.ui.savingsgoal.SavingsGoalViewLocked
import com.shahzadian.goalsscreen.ui.savingsgoal.SavingsGoalViewNew
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewCommand
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewEvent
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewModel
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewState

@Composable
fun GoalsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LocalView.current
    val viewModel: GoalsViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is GoalsViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is GoalsViewState.Data -> {
            ContentView(
                data = state as GoalsViewState.Data,
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
                    is GoalsViewCommand.NavigateTo -> {
                        navController.navigate(command.appScreen.route)
                    }

                    is GoalsViewCommand.NavigateToAchievementShareScreen -> {
                        navController.navigate(AppScreen.AchievementShareScreen.composeRoute(command.id))
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: GoalsViewState.Data,
    onEvent: (GoalsViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onSavingsGoalsViewEvent = remember {
        { onEvent(GoalsViewEvent.OnSavingsGoalsUpgradeClicked) }
    }
    val onShowMoreAchievementsClickedEvent = remember {
        { onEvent(GoalsViewEvent.OnShowMoreAchievementsClicked) }
    }
    val onAchievementShareClick: (id: String) -> Unit = remember {
        { onEvent(GoalsViewEvent.OnAchievementShareClicked(it)) }
    }
    val onSavingsGoalCreateClick: () -> Unit = remember {
        { onEvent(GoalsViewEvent.OnSavingsGoalCreateClicked) }
    }
    val onSavingsGoalDetailsClick: () -> Unit = remember {
        { onEvent(GoalsViewEvent.OnSavingsGoalDetailsClicked) }
    }

    HeaderScreen(
        title = TextWrapper.FromRes(R.string.goals_header_title),
        subtitle = TextWrapper.FromRes(R.string.goals_header_subtitle),
        modifier = modifier,
        icon = ImageWrapper.FromRes(com.shahzadian.goalsscreen.R.drawable.ic_goals_header)
    ) {
        if (data.hasPaidSubscription) {
            data.savingsGoal?.let { savingsGoal ->
                SavingsGoalViewExisting(
                    savingsGoal = savingsGoal,
                    onDetailsClicked = onSavingsGoalDetailsClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable {
                            onSavingsGoalDetailsClick()
                        }
                )
            } ?: SavingsGoalViewNew(
                onCreateButtonClicked = onSavingsGoalCreateClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

        } else {
            SavingsGoalViewLocked(
                onUpgradeClicked = onSavingsGoalsViewEvent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        AchievementsBlockView(
            data = data.achievementsData,
            onMoreClick = onShowMoreAchievementsClickedEvent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            onAchievementShareClicked = onAchievementShareClick
        )

    }
}


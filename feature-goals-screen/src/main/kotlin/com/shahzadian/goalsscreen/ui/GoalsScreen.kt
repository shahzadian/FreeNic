package com.shahzadian.goalsscreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.goals.ui.LockedGoalsView
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewCommand
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewEvent
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewModel
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewState

@Composable
fun GoalsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: GoalsViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is GoalsViewState.Loading -> {}
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
    val onSavingGoalsViewEvent = remember {
        { onEvent(GoalsViewEvent.OnSavingGoalsUpgradeClicked) }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.goals_header_title),
        subtitle = TextWrapper.FromRes(R.string.goals_header_subtitle),
        modifier = modifier,
        icon = ImageWrapper.FromRes(com.shahzadian.goalsscreen.R.drawable.ic_goals_header)
    ) {
        LockedGoalsView(
            onUpgradeClicked = onSavingGoalsViewEvent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        AchievementsBlockView(
            data = data,
            modifier = Modifier.fillMaxWidth()
        )

    }
}


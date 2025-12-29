package com.shahzadian.savingsgoalscreen.ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.details.viewmodel.SavingsGoalDetailsViewModel
import com.shahzadian.savingsgoalscreen.ui.details.viewmodel.SavingsGoalDetailsViewState

@Composable
fun SavingsGoalDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: SavingsGoalDetailsViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is SavingsGoalDetailsViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is SavingsGoalDetailsViewState.Data -> {
            ContentView(
                data = (state as SavingsGoalDetailsViewState.Data),
                onBackIconClicked = { navController.popBackStack() },
                modifier = modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: SavingsGoalDetailsViewState.Data,
    onBackIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backIconClick = remember {
        { onBackIconClicked() }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.savings_goal_details_header),
        hasBackIcon = true,
        onBackIconClicked = backIconClick,
        modifier = modifier,
    ) {
        SavingsGoalDetailsCardView(
            dataUiModel = data.uiModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        SavingsGoalDetailsInfoView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        SavingsGoalDetailsFooterView(
            onEditClicked = {},
            onDeleteClicked = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

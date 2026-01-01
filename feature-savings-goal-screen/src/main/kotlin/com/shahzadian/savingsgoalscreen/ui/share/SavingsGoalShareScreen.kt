package com.shahzadian.savingsgoalscreen.ui.share

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.share.templates.SavingsGoalShareOrangeCard
import com.shahzadian.savingsgoalscreen.ui.share.viewmodel.SavingsGoalShareViewModel
import com.shahzadian.savingsgoalscreen.ui.share.viewmodel.SavingsGoalShareViewState

@Composable
fun SavingsGoalShareScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val backClick: () -> Unit = remember {
        { navController.popBackStack() }
    }
    val viewModel: SavingsGoalShareViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is SavingsGoalShareViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize(),
            )
        }

        is SavingsGoalShareViewState.Data -> {
            ContentView(
                data = (state as SavingsGoalShareViewState.Data),
                onBackIconClicked = backClick,
                modifier = modifier
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: SavingsGoalShareViewState.Data,
    onBackIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backIconClick = remember {
        { onBackIconClicked() }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.savings_goal_share_title),
        modifier = modifier,
        subtitle = null,
        icon = null,
        hasBackIcon = true,
        onBackIconClicked = backIconClick,
    ) {
        SavingsGoalShareOrangeCard(
            dataUiModel = data.dataUiModel,
            isSelected = true,
            shouldRecord = false,
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

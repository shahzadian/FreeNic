package com.shahzadian.savingsgoalscreen.ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.details.viewmodel.SavingsGoalDetailsViewCommand
import com.shahzadian.savingsgoalscreen.ui.details.viewmodel.SavingsGoalDetailsViewEvent
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
                onShareClicked = {
                    navController.navigate(AppScreen.SavingsGoalShareScreen.route)
                },
                onEditClicked = {
                    // Navigate to create screen in edit mode
                    navController.navigate(
                        AppScreen.SavingsGoalCreateScreen.composeRoute(true)
                    )
                },
                onDeleteClicked = {
                    viewModel.onViewEvent(SavingsGoalDetailsViewEvent.OnDeleteClicked)
                },
                modifier = modifier.fillMaxSize(),
            )
        }
    }

    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel.viewCommandFlow.collect { command ->
            when (command) {
                is SavingsGoalDetailsViewCommand.NavigateBack -> navController.popBackStack()
            }
        }
    }
}

@Composable
private fun ContentView(
    data: SavingsGoalDetailsViewState.Data,
    onBackIconClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val backIconClick = remember {
        { onBackIconClicked() }
    }
    val shareIconClick = remember { { onShareClicked() } }
    val editClick = remember { { onEditClicked() } }
    val deleteClick = remember { { showDeleteDialog = true } }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.savings_goal_details_header),
        hasBackIcon = true,
        onBackIconClicked = backIconClick,
        modifier = modifier,
        icon = ImageWrapper.FromRes(com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_details_share),
        onIconClicked = shareIconClick,
    ) {
        SavingsGoalDetailsCardView(
            dataUiModel = data.uiModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        SavingsGoalDetailsFooterView(
            onEditClicked = editClick,
            onDeleteClicked = deleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        SavingsGoalDetailsInfoView(
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = stringResource(R.string.savings_goal_delete_confirm_title)) },
            text = {
                Text(
                    text = stringResource(R.string.savings_goal_delete_confirm_message),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDeleteClicked()
                }) {
                    Text(text = stringResource(R.string.savings_goal_delete_confirm_yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = stringResource(R.string.savings_goal_delete_confirm_no))
                }
            }
        )
    }
}

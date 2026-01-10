package com.shahzadian.slipscreen.ui.slipreason.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.MainToolbarView
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel.SlipReasonViewCommand
import com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel.SlipReasonViewEvent
import com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel.SlipReasonViewModel
import com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel.SlipReasonViewState

@Composable
fun SlipReasonScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: SlipReasonViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is SlipReasonViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is SlipReasonViewState.Data -> {
            ContentView(
                data = (state as SlipReasonViewState.Data),
                onBackIconClicked = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize()
            )
        }
    }
    LaunchedEffect(viewModel) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is SlipReasonViewCommand.NavigateBack -> {
                        navController.popBackStack()
                    }

                    is SlipReasonViewCommand.NavigateTo -> {
                        navController.navigate(command.screen.route)
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: SlipReasonViewState.Data,
    onBackIconClicked: () -> Unit,
    onEvent: (SlipReasonViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onBackIconClick = remember(Unit) { { onBackIconClicked() } }
    val onReasonClick =
        remember(Unit) { { id: String -> onEvent(SlipReasonViewEvent.OnReasonClicked(id)) } }
    val onConfirmClick = remember(Unit) { { onEvent(SlipReasonViewEvent.OnConfirmClicked) } }
    val onSkipClick = remember(Unit) { { onEvent(SlipReasonViewEvent.OnSkipClicked) } }
    Box(
        modifier = modifier
            .padding(16.dp),
    ) {
        MainToolbarView(
            title = TextWrapper.FromRes(R.string.slip_context_toolbar_title),
            subtitle = TextWrapper.FromRes(R.string.slip_context_toolbar_subtitle),
            hasBackIcon = true,
            onBackIconClicked = onBackIconClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )
        MainDataView(
            data = data,
            onReasonClicked = onReasonClick,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(vertical = 96.dp),
        )
        FooterView(
            isConfirmButtonEnabled = data.isSubmitButtonEnabled,
            onConfirmClicked = onConfirmClick,
            onSkipClicked = onSkipClick,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun MainDataView(
    data: SlipReasonViewState.Data,
    onReasonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onReasonClick = remember { { id: String -> onReasonClicked(id) } }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ReasonsView(
            data = data,
            onClicked = onReasonClick,
            modifier = Modifier.fillMaxWidth(),
        )
        DateView(
            data = data,
            modifier = Modifier.padding(bottom = 64.dp)
        )
    }
}

@Composable
private fun ReasonsView(
    data: SlipReasonViewState.Data,
    onClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onClick = remember { { id: String -> onClicked(id) } }
    Column(
        modifier = modifier,
    ) {
        data
            .slipReasons
            .forEachIndexed { index, reasonUiModel ->
                SlipReasonView(
                    data = reasonUiModel,
                    isSelected = data.selectedReasonId == reasonUiModel.id,
                    onClicked = {
                        onClick(reasonUiModel.id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)

                )
            }
    }
}

@Composable
private fun DateView(
    data: SlipReasonViewState.Data,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.slip_reason_date_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(R.string.slip_reason_date_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        SlipDateView(
            data = data.slipDateUiModel,
            onDateConfirmed = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Composable
private fun FooterView(
    isConfirmButtonEnabled: Boolean,
    onConfirmClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val confirmClick = remember(Unit) { { onConfirmClicked() } }
    val skipClick = remember(Unit) { { onSkipClicked() } }
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.slip_context_submit),
            enabled = isConfirmButtonEnabled,
            onClick = confirmClick,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
        )
        Text(
            text = stringResource(R.string.slip_context_skip),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.clickable(onClick = skipClick),
        )
    }
}
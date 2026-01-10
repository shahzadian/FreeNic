package com.shahzadian.slipscreen.ui.slipcontext.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.shahzadian.slipscreen.ui.slipcontext.ui.viewmodel.SlipContextViewEvent
import com.shahzadian.slipscreen.ui.slipcontext.ui.viewmodel.SlipContextViewModel
import com.shahzadian.slipscreen.ui.slipcontext.ui.viewmodel.SlipContextViewState

@Composable
fun SlipContextScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: SlipContextViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is SlipContextViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is SlipContextViewState.Data -> {
            ContentView(
                onBackIconClicked = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ContentView(
    onBackIconClicked: () -> Unit,
    onEvent: (SlipContextViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onBackIconClick = remember(Unit) { { onBackIconClicked() } }
    val onConfirmClick = remember(Unit) { { onEvent(SlipContextViewEvent.OnConfirmClicked) } }
    val onSkipClick = remember(Unit) { { onEvent(SlipContextViewEvent.OnSkipClicked) } }
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
        FooterView(
            onConfirmClicked = onConfirmClick,
            onSkipClicked = onSkipClick,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun FooterView(
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
package com.shahzadian.slipscreen.ui.slipcontext.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
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
    }
}

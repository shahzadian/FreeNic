package com.shahzadian.cravingsscreen.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahzadian.cravingsscreen.R
import com.shahzadian.cravingsscreen.ui.viewmodel.CravingsViewCommand
import com.shahzadian.cravingsscreen.ui.viewmodel.CravingsViewModel
import com.shahzadian.cravingsscreen.ui.viewmodel.CravingsViewState

@Composable
fun CravingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: CravingsViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is CravingsViewState.Loading -> {}
        is CravingsViewState.Data -> {
            ContentView(
                data = (state as CravingsViewState.Data),
                modifier = modifier,
            )
        }
    }
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is CravingsViewCommand.NavigateTo -> {
                        navController.navigate(command.screen.route)
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: CravingsViewState.Data,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        HeaderView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 16.dp)
        )
        CravingsToolsView(
            data = data,
        )
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderIconView(
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.cravings_header_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 12.dp),
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.cravings_header_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun HeaderIconView(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "Title scale")
    val scale by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Title scale"
    )
    Image(
        painter = painterResource(R.drawable.ic_cravings_main),
        contentDescription = "header",
        modifier = modifier
            .scale(scale),
    )
}
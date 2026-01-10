package com.shahzadian.slipscreen.ui.slipconfirmation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.screenHeight
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.slipscreen.R
import com.shahzadian.slipscreen.ui.slipconfirmation.viewmodel.SLipConfirmationViewModel
import com.shahzadian.slipscreen.ui.slipconfirmation.viewmodel.SlipConfirmationViewState

@Composable
fun SlipConfirmationScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: SLipConfirmationViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is SlipConfirmationViewState.Data -> {
            ContentView(
                onConfirmed = {},
                onCanceled = {},
                modifier = modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun ContentView(
    onConfirmed: () -> Unit,
    onCanceled: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val confirmed = remember {
        { onConfirmed() }
    }
    val canceled = remember {
        { onCanceled() }
    }
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val currentScreenHeight = screenHeight.dp
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = currentScreenHeight / 4),
        ) {
            ImageView(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
            )
            MainContentView(
                onConfirmed = confirmed,
                onCanceled = canceled,
            )
        }
    }
}

@Composable
private fun ImageView(
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 500),
        ),
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_slip_confirmation),
            contentDescription = "Slip Confirmation",
        )
    }
}

@Composable
private fun MainContentView(
    onConfirmed: () -> Unit,
    onCanceled: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val confirmed = remember {
        { onConfirmed() }
    }
    val canceled = remember {
        { onCanceled() }
    }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 300,
            ),
        ),
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.slip_confirmation_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.slip_confirmation_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
            )
            MainSubmitButton(
                onClick = confirmed,
                text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.slip_confirmation_submit),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.slip_confirmation_cancel),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .clickable(onClick = canceled),
            )
        }
    }
}

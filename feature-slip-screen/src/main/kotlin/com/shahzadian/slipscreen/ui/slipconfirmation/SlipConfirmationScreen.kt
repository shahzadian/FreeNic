package com.shahzadian.slipscreen.ui.slipconfirmation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.extensions.MainSubmitButton
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
                modifier = modifier.fillMaxSize()
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
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_slip_confirmation),
            contentDescription = "Slip Confirmation",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.slip_confirmation_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.slip_confirmation_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        MainSubmitButton(
            onClick = confirmed,
            text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.slip_confirmation_submit),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.slip_confirmation_cancel),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.clickable(onClick = canceled),
        )
    }
}

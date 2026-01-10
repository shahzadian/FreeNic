package com.shahzadian.slipscreen.ui.slipresult

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.shahzadian.slipscreen.R
import com.shahzadian.slipscreen.ui.slipresult.viewmodel.SlipResultViewModel
import com.shahzadian.slipscreen.ui.slipresult.viewmodel.SlipResultViewState

@Composable
fun SlipResultScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: SlipResultViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is SlipResultViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is SlipResultViewState.Data -> {
            ContentView(
                data = (state as SlipResultViewState.Data),
                modifier = modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: SlipResultViewState.Data,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_slip_result),
                contentDescription = "Slip result",
                modifier = Modifier.padding(bottom = 32.dp)
            )
            TextsView(
                data = data,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
private fun TextsView(
    data: SlipResultViewState.Data,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.slip_result_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            textAlign = TextAlign.Center,
        )
        val pluralDays =
            pluralStringResource(
                com.shahzadian.baseui.R.plurals.quantity_days,
                data.noCigDays,
                data.noCigDays
            )
        val subtitle =
            stringResource(com.shahzadian.baseui.R.string.slip_result_subtitle, pluralDays)
        val daysIndex = subtitle.indexOf(pluralDays)
        val firstPart = subtitle.take(daysIndex)
        val days = subtitle.substring(daysIndex, daysIndex + pluralDays.length)
        val secondPart = subtitle.substring(daysIndex + pluralDays.length, subtitle.length)
        Text(
            text = buildAnnotatedString {
                if (daysIndex > 0) {
                    append(firstPart)
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(days)
                    }
                    append(secondPart)
                } else {
                    append(subtitle)
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.slip_result_secondary),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
        )
    }
}
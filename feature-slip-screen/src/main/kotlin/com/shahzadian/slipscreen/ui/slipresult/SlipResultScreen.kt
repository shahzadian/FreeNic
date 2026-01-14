package com.shahzadian.slipscreen.ui.slipresult

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.mainHintBackground
import com.shahzadian.slipscreen.R
import com.shahzadian.slipscreen.ui.slipresult.viewmodel.SlipResultViewCommand
import com.shahzadian.slipscreen.ui.slipresult.viewmodel.SlipResultViewEvent
import com.shahzadian.slipscreen.ui.slipresult.viewmodel.SlipResultViewModel
import com.shahzadian.slipscreen.ui.slipresult.viewmodel.SlipResultViewState

@Composable
fun SlipResultScreen(
    navigator: AppNavigator,
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
                onEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize(),
            )
        }
    }
    LaunchedEffect(viewModel) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is SlipResultViewCommand.NavigateTo -> {
                        navigator.navigateToScreen(command.screen)
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: SlipResultViewState.Data,
    onEvent: (SlipResultViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onReadNotesClick = remember {
        { onEvent(SlipResultViewEvent.OnReadMotivationalNotesClicked) }
    }
    val onBackToDashboardClick = remember {
        { onEvent(SlipResultViewEvent.OnBackToDashboardClicked) }
    }
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
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
            DividerView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            HintView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            ReadNotesButton(
                onReadNotesClicked = onReadNotesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 8.dp),
            )
            BackToDashboardButton(
                onBackToDashboardClicked = onBackToDashboardClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
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

@Composable
private fun DividerView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(
            modifier = Modifier
                .padding(end = 2.dp)
                .weight(4f),
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp,
        )
        Image(
            painter = painterResource(R.drawable.ic_slip_result_divider),
            contentDescription = "Divider",
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .weight(1f)
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(start = 2.dp)
                .weight(4f),
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp,
        )
    }
}

@Composable
private fun HintView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .mainHintBackground()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_slip_result_hint),
            contentDescription = "Hint",
            modifier = Modifier.padding(end = 8.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.slip_result_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun ReadNotesButton(
    onReadNotesClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onReadNotesClick = remember(Unit) { { onReadNotesClicked() } }
    Button(
        onClick = onReadNotesClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_slip_result_motivation_note),
                contentDescription = "Motivation note",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.slip_result_read_motvation_note),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
private fun BackToDashboardButton(
    onBackToDashboardClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backToDashboardClick = remember(Unit) { { onBackToDashboardClicked() } }
    Button(
        onClick = backToDashboardClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_slip_result_back_to_dashboard),
                contentDescription = "Back to dashboard",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.slip_result_back_to_dashboard),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}
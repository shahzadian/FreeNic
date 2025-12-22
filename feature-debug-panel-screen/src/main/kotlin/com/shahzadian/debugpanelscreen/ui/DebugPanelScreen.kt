package com.shahzadian.debugpanelscreen.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.debugpanelscreen.ui.viewmodel.DebugPanelViewEvent
import com.shahzadian.debugpanelscreen.ui.viewmodel.DebugPanelViewModel
import com.shahzadian.debugpanelscreen.ui.viewmodel.DebugPanelViewState

@Composable
fun DebugPanelScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: DebugPanelViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is DebugPanelViewState.Loading -> {}
        is DebugPanelViewState.Data -> {
            ContentView(
                data = (state as DebugPanelViewState.Data),
                onEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: DebugPanelViewState.Data,
    onEvent: (DebugPanelViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onHasPaidSubscriptionSwitchEvent: (Boolean) -> Unit = remember {
        {
            onEvent(
                DebugPanelViewEvent.OnHasPaidSubscriptionSwitched(
                    isSwitchOn = it
                )
            )
        }
    }
    HeaderScreen(
        title = TextWrapper.FromString("Debug Panel"),
        subtitle = TextWrapper.FromString("Set debug options"),
        modifier = modifier,
    ) {
        HasPaidSubscriptionView(
            hasPaidSubscription = data.hasPaidSubscription,
            onSwitchChanged = onHasPaidSubscriptionSwitchEvent,
        )
    }
}

@Composable
private fun HasPaidSubscriptionView(
    hasPaidSubscription: Boolean,
    onSwitchChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onSwitchClick: (Boolean) -> Unit = remember { { onSwitchChanged(it) } }
    Row(
        modifier = modifier,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Text(
            text = "Has paid subscription",
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge,
        )
        Switch(
            checked = hasPaidSubscription,
            onCheckedChange = onSwitchClick,
        )
    }
}

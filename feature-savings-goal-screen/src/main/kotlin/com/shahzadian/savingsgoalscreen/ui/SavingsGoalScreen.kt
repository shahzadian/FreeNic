package com.shahzadian.savingsgoalscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.viewmodel.SavingsGoalCreateViewEvent
import com.shahzadian.savingsgoalscreen.ui.viewmodel.SavingsGoalCreateViewModel
import com.shahzadian.savingsgoalscreen.ui.viewmodel.SavingsGoalCreateViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SavingsGoalCreateScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: SavingsGoalCreateViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is SavingsGoalCreateViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize(),
            )
        }

        is SavingsGoalCreateViewState.Data -> {
            ContentView(
                data = (state as SavingsGoalCreateViewState.Data),
                onEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ContentView(
    data: SavingsGoalCreateViewState.Data,
    onEvent: (SavingsGoalCreateViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val onGoalNameChange: (String) -> Unit = { onEvent(SavingsGoalCreateViewEvent.OnGoalNameChanged(it)) }
    val onAmountChange: (String) -> Unit = { onEvent(SavingsGoalCreateViewEvent.OnAmountChanged(it)) }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.savings_goal_header_title),
        modifier = modifier
    ) {
        HintView(
            modifier = Modifier.padding(bottom = 16.dp)
        )
        InputView(
            label = stringResource(R.string.savings_goal_create_label_name),
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            InputField(
                value = data.goalName,
                hint = stringResource(R.string.savings_goal_create_input_hint_name),
                onValueChange = onGoalNameChange,
                hasNextField = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
        InputView(
            label = stringResource(R.string.savings_goal_create_label_amount),
        ) {
            InputField(
                value = data.goalAmount,
                hint = stringResource(R.string.savings_goal_create_hint_amount),
                onValueChange = onAmountChange,
                hasNextField = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HintView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(com.shahzadian.savingsgoalscreen.R.drawable.ic_saving_goal_hint),
            contentDescription = "Hint",
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.savings_goal_create_hint_title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.savings_goal_create_hint_desc),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun InputView(
    label: String,
    modifier: Modifier = Modifier,
    input: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(bottom = 8.dp),
        )
        input()
    }
}

@Composable
internal fun InputField(
    value: String?,
    hint: String? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    hasNextField: Boolean = false,
    trailingContent: (@Composable (() -> Unit))? = null,
) {
    // Throttle rapid value changes so we don't call the external callback too frequently
    val coroutineScope = rememberCoroutineScope()
    var debounceJob: Job? by remember { mutableStateOf<Job?>(null) }
    var localText by remember { mutableStateOf(value) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Keep local state in sync with external value (e.g., from ViewModel)
    LaunchedEffect(value) {
        if (value != localText) localText = value
    }

    TextField(
        value = localText ?: "",
        onValueChange = { newText ->
            localText = newText
            // Restart debounce timer
            debounceJob?.cancel()
            debounceJob = coroutineScope.launch {
                delay(300)
                onValueChange(newText)
            }
        },
        modifier = modifier
            .clip(MaterialTheme.shapes.medium),
        keyboardOptions = keyboardOptions.copy(
            imeAction = if (hasNextField) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                // Move focus to the next focusable TextField if available
                focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = {
                // Hide the keyboard when this is the last field
                keyboardController?.hide()
            }
        ),
        trailingIcon = trailingContent,
        placeholder = {
            hint?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        singleLine = true,
    )
}
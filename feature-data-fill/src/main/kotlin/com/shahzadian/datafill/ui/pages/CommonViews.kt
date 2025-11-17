package com.shahzadian.datafill.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun DataFillHeader(
    title: TextWrapper,
    subtitle: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = textFromWrapper(title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = textFromWrapper(subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
internal fun DataFillInputLabel(
    text: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Text(
        text = textFromWrapper(text),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier,
    )
}

@Composable
internal fun DataFillInputDescription(
    text: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Text(
        text = textFromWrapper(text),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier,
    )
}

@Composable
internal fun DataFillInputField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    hasNextField: Boolean = false,
    modifier: Modifier = Modifier,
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
        value = localText,
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
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            )
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
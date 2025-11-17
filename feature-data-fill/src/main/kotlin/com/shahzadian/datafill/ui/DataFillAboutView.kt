package com.shahzadian.datafill.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.foundation.text.KeyboardActions
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun DataFillAboutView(
    data: DataFillViewState.About,
    onNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState),
    ) {
        Header(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp)
        )
        NameBlock(
            value = data.name ?: "",
            onValueChange = onNameChanged,
            modifier = Modifier
                .padding(bottom = 24.dp),
        )
        AgeBlock(
            value = data.age ?: "",
            onValueChange = onAgeChanged,
            modifier = Modifier
        )
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.data_fill_about_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = stringResource(R.string.data_fill_about_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun NameBlock(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FieldBlock(
        label = TextWrapper.FromRes(R.string.data_fill_about_field_name_label),
        description = TextWrapper.FromRes(R.string.data_fill_about_field_name_desc),
        inputField = { inputModifier ->
            InputField(
                value = value,
                hint = stringResource(R.string.data_fill_about_field_name_hint),
                onValueChange = onValueChange,
                hasNextField = true,
                modifier = inputModifier,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun AgeBlock(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FieldBlock(
        label = TextWrapper.FromRes(R.string.data_fill_about_field_age_label),
        description = TextWrapper.FromRes(R.string.data_fill_about_field_age_desc),
        inputField = { inputModifier ->
            InputField(
                value = value,
                hint = stringResource(R.string.data_fill_about_field_age_hint),
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                hasNextField = false,
                modifier = inputModifier,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun FieldBlock(
    label: TextWrapper,
    description: TextWrapper,
    inputField: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = textFromWrapper(label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        inputField(Modifier.fillMaxWidth())
        Text(
            text = textFromWrapper(description),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(top = 8.dp, end = 2.dp)
        )
    }
}

@Composable
fun InputField(
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

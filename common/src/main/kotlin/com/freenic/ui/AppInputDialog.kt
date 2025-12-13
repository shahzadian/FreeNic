package com.freenic.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.shahzadian.baseui.R

/**
 * Универсальный диалог редактирования текста с единым стилем полей ввода.
 * Использует Material3 AlertDialog и OutlinedTextField.
 *
 * - Кнопки OK/Cancel располагаются корректно за счёт confirmButton/dismissButton.
 * - Поддерживает валидацию (isConfirmEnabled) и фильтрацию ввода (valueFilter).
 */
@Composable
fun AppInputDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
    title: String? = null,
    label: String? = null,
    initialValue: String = "",
    confirmText: String = stringResource(R.string.date_picker_dialog_ok),
    dismissText: String = stringResource(R.string.date_picker_dialog_cancel),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    valueFilter: (String) -> String = { it },
    isConfirmEnabled: (String) -> Boolean = { true },
    validate: (String) -> Int? = { null },
) {
    val textState = remember(initialValue) { mutableStateOf(initialValue) }
    val errorResId = validate(textState.value)
    val errorText = errorResId?.let { stringResource(it) }
    val canConfirm = isConfirmEnabled(textState.value) && errorText == null

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = { onConfirm(textState.value) },
                enabled = canConfirm
            ) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = dismissText)
            }
        },
        title = title?.let {
            { Text(text = it, color = MaterialTheme.colorScheme.onPrimary) }
        },
        text = {
            OutlinedTextField(
                value = textState.value,
                onValueChange = { new -> textState.value = valueFilter(new) },
                label = label?.let { { Text(text = it) } },
                keyboardOptions = keyboardOptions,
                singleLine = true,
                isError = errorText != null,
                supportingText = if (errorText != null) {
                    { Text(text = errorText) }
                } else null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    )
}

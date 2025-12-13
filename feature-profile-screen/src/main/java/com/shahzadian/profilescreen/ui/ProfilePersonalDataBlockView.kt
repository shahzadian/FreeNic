package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.AppDatePickerDialog
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.ui.common.ProfileSettingsBlockView
import com.shahzadian.profilescreen.ui.common.ProfileSettingsFieldView
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewEvent
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.freenic.ui.AppInputDialog

@Composable
internal fun ProfilePersonalDataBlockView(
    data: ProfileViewState.Data.PersonalData,
    onViewEvent: (ProfileViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDateDialogShown by remember { mutableStateOf(false) }
    var isNameDialogShown by remember { mutableStateOf(false) }
    var isAgeDialogShown by remember { mutableStateOf(false) }
    ProfileSettingsBlockView(
        title = stringResource(R.string.profile_title_block_personal_info),
        iconRes = com.shahzadian.profilescreen.R.drawable.ic_profile_personal_info,
        modifier = modifier,
    ) {
        ProfileSettingsFieldView(
            label = stringResource(R.string.profile_label_name),
            value = data.name,
            onIconClick = { isNameDialogShown = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        val pluralStringResource = pluralStringResource(R.plurals.profile_age_postfix, data.age)
        val displayableAge = "${data.age} $pluralStringResource"
        ProfileSettingsFieldView(
            label = stringResource(R.string.profile_label_age),
            value = displayableAge,
            onIconClick = { isAgeDialogShown = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        ProfileSettingsFieldView(
            label = stringResource(R.string.profile_quit_date),
            value = data.quitDate,
            onIconClick = { isDateDialogShown = true }
        )
    }
    if (isNameDialogShown) {
        AppInputDialog(
            onDismissRequest = { isNameDialogShown = false },
            onConfirm = { newValue ->
                val value = newValue.trim()
                if (value.isNotEmpty()) {
                    onViewEvent(ProfileViewEvent.OnNameEdited(value))
                    isNameDialogShown = false
                }
            },
            title = stringResource(R.string.profile_label_name),
            initialValue = data.name,
            isConfirmEnabled = { it.trim().isNotEmpty() },
        )
    }
    if (isAgeDialogShown) {
        AppInputDialog(
            onDismissRequest = { isAgeDialogShown = false },
            onConfirm = { text ->
                val value = text.toIntOrNull()
                if (value != null && value in UserDataValidator.MIN_AGE..UserDataValidator.MAX_AGE) {
                    onViewEvent(ProfileViewEvent.OnAgeEdited(value))
                    isAgeDialogShown = false
                }
            },
            title = stringResource(R.string.profile_label_age),
            initialValue = data.age.toString(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            valueFilter = { it.filter { ch -> ch.isDigit() } },
            isConfirmEnabled = { it.toIntOrNull()?.let { v -> v in UserDataValidator.MIN_AGE..UserDataValidator.MAX_AGE } == true },
            validate = { txt ->
                UserDataValidator.validateAgeText(txt)
            },
        )
    }
    if (isDateDialogShown) {
        AppDatePickerDialog(
            maxDateMillis = System.currentTimeMillis(),
            onDismissRequest = { isDateDialogShown = false },
            onDateConfirmed = { result ->
                onViewEvent(ProfileViewEvent.OnQuitDateSelected(result.millis))
                isDateDialogShown = false
            }
        )
    }
}

package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

@Composable
internal fun ProfilePersonalDataBlockView(
    data: ProfileViewState.Data.PersonalData,
    onViewEvent: (ProfileViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDateDialogShown by remember { mutableStateOf(false) }
    ProfileSettingsBlockView(
        title = stringResource(R.string.profile_title_block_personal_info),
        iconRes = com.shahzadian.profilescreen.R.drawable.ic_profile_personal_info,
        modifier = modifier,
    ) {
        ProfileSettingsFieldView(
            label = stringResource(R.string.profile_label_name),
            value = data.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        val pluralStringResource = pluralStringResource(R.plurals.profile_age_postfix, data.age)
        val displayableAge = "${data.age} $pluralStringResource"
        ProfileSettingsFieldView(
            label = stringResource(R.string.profile_label_age),
            value = displayableAge,
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

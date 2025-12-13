package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.freenic.ui.AppInputDialog
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.ui.common.ProfileSettingsBlockView
import com.shahzadian.profilescreen.ui.common.ProfileSettingsFieldView
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewEvent
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewState

@Composable
internal fun ProfileSmokingDetailsBlockView(
    data: ProfileViewState.Data.SmokingDetails,
    onViewEvent: (ProfileViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ProfileSettingsBlockView(
        title = stringResource(R.string.profile_title_block_smoking_details),
        iconRes = com.shahzadian.profilescreen.R.drawable.ic_profile_smoking_details,
        modifier = modifier,
    ) {
        val showCigsDialog = remember { mutableStateOf(false) }
        val showPriceDialog = remember { mutableStateOf(false) }

        val cigsPerDay = data.cigarettesPerDay
        val plural = pluralStringResource(R.plurals.profile_cigs_per_day_postfix, cigsPerDay)
        val displayableCigsPerDay = "$cigsPerDay $plural"
        ProfileSettingsFieldView(
            label = stringResource(R.string.profile_cigs_per_day),
            value = displayableCigsPerDay,
            onIconClick = { showCigsDialog.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        val displayablePrice = data.currency
            ?.let { currency -> "$currency${data.price}" }
            ?: data.price.toString()
        ProfileSettingsFieldView(
            label = stringResource(R.string.profile_cigs_price),
            value = displayablePrice,
            onIconClick = { showPriceDialog.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        if (showCigsDialog.value) {
            AppInputDialog(
                onDismissRequest = { showCigsDialog.value = false },
                onConfirm = { text ->
                    val value = text.toIntOrNull()
                    if (value != null) {
                        onViewEvent(ProfileViewEvent.OnCigsPerDayEdited(value))
                        showCigsDialog.value = false
                    }
                },
                title = stringResource(R.string.profile_cigs_per_day),
                initialValue = cigsPerDay.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                valueFilter = { it.filter { ch -> ch.isDigit() } },
                isConfirmEnabled = { it.toIntOrNull() != null }
            )
        }

        if (showPriceDialog.value) {
            AppInputDialog(
                onDismissRequest = { showPriceDialog.value = false },
                onConfirm = { text ->
                    val value = text.toFloatOrNull()
                    if (value != null && value >= UserDataValidator.MIN_PRICE) {
                        onViewEvent(ProfileViewEvent.OnPriceEdited(value))
                        showPriceDialog.value = false
                    }
                },
                title = stringResource(R.string.profile_cigs_price),
                initialValue = data.price.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                valueFilter = { new -> new.replace(',', '.').filter { it.isDigit() || it == '.' } },
                isConfirmEnabled = { it.toFloatOrNull()?.let { v -> v >= UserDataValidator.MIN_PRICE } == true },
                validate = { txt ->
                    UserDataValidator.validatePriceText(txt)
                },
            )
        }
    }
}

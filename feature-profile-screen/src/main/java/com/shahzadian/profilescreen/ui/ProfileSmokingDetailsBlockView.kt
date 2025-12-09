package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.ui.common.ProfileSettingsBlockView
import com.shahzadian.profilescreen.ui.common.ProfileSettingsFieldView
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewState

@Composable
internal fun ProfileSmokingDetailsBlockView(
    data: ProfileViewState.Data.SmokingDetails,
    modifier: Modifier = Modifier,
) {
    ProfileSettingsBlockView(
        title = stringResource(R.string.profile_title_block_smoking_details),
        iconRes = com.shahzadian.profilescreen.R.drawable.ic_profile_smoking_details,
        modifier = modifier,
    ) {
        val cigsPerDay = data.cigarettesPerDay
        val plural = pluralStringResource(R.plurals.profile_cigs_per_day_postfix, cigsPerDay)
        val displayableCigsPerDay = "$cigsPerDay $plural"
        ProfileSettingsFieldView(
            label = stringResource(R.string.profile_cigs_per_day),
            value = displayableCigsPerDay,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

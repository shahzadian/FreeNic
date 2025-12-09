package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.ui.common.ProfileSettingsBlockView
import com.shahzadian.profilescreen.ui.common.ProfileSettingsField
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewState

@Composable
internal fun ProfilePersonalDataBlockView(
    data: ProfileViewState.Data.PersonalData,
    modifier: Modifier = Modifier,
) {
    ProfileSettingsBlockView(
        title = TextWrapper.FromRes(
            R.string.profile_title_personal_info
        ),
        modifier = modifier,
    ) {
        ProfileSettingsField(
            label = TextWrapper.FromRes(R.string.profile_label_name),
            value = data.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        val ageString = textFromWrapper(data.age)
        val pluralStringResource = pluralStringResource(R.plurals.profile_age_postfix, ageString.toInt())
        val displayableAge = "$ageString $pluralStringResource"
        ProfileSettingsField(
            label = TextWrapper.FromRes(R.string.profile_label_age),
            value = TextWrapper.FromString(displayableAge),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        ProfileSettingsField(
            label = TextWrapper.FromRes(R.string.profile_quit_date),
            value = data.quitDate,
        )
    }
}

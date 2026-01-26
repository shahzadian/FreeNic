package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.noIndicationClick
import com.shahzadian.profilescreen.R

@Composable
internal fun ProfilePrivacyBlockView(
    onPrivacyClicked: () -> Unit,
    onTermsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        ItemRow(
            labelRes = com.shahzadian.baseui.R.string.profile_privacy,
            iconRes = R.drawable.ic_profile_privacy,
            onClicked = onPrivacyClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
            modifier = Modifier.padding(bottom = 16.dp),
        )
        ItemRow(
            labelRes = com.shahzadian.baseui.R.string.profile_terms,
            iconRes = R.drawable.ic_profile_terms,
            onClicked = onTermsClicked,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ItemRow(
    labelRes: Int,
    iconRes: Int,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val click = remember { { onClicked() } }
    Row(
        modifier = modifier
            .noIndicationClick(onClick = click),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = stringResource(labelRes),
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_profile_privacy_arrow),
            contentDescription = "arrow",
        )
    }
}

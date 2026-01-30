package com.shahzadian.profilescreen.ui.subscriptionview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
internal fun HasSubscriptionView(
    activeUntilDate: String,
    onManageSubscriptionClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val manageSubscriptionClick = remember {
        { onManageSubscriptionClicked() }
    }
    Column(
        modifier = modifier
            .mainCardBackground()
            .clickable(onClick = manageSubscriptionClick)
            .padding(16.dp)
    ) {
        HeaderView(
            activeUntilDate = activeUntilDate,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
            modifier = Modifier.padding(bottom = 16.dp),
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.profile_premium_manage_button),
            modifier = Modifier.fillMaxWidth(),
            onClick = manageSubscriptionClick,
        )
    }
}

@Composable
private fun HeaderView(
    activeUntilDate: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_premium),
                contentDescription = "premium",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.profile_premium_active),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Text(
            text = stringResource(R.string.profile_premium_active_until, activeUntilDate),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

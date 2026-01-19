package com.shahzadian.profilescreen.managesubscription

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.managesubscription.viewmodel.ManageSubscriptionViewState

@Composable
internal fun CurrentPlanView(
    data: ManageSubscriptionViewState.Data,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.manage_subscription_current_plan_premium_badge),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = textFromWrapper(data.currentPlanTitle),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(com.shahzadian.profilescreen.R.drawable.ic_manage_subscription_current_plan_calendar),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
                contentDescription = "Renewal date",
                modifier = Modifier.padding(end = 8.dp)
            )
            val text = if (data.isAutoRenewing) {
                stringResource(
                    R.string.manage_subscription_current_plan_premium_renewal,
                    data.activeUntil
                )
            } else {
                stringResource(
                    R.string.manage_subscription_current_plan_premium_active_until,
                    data.activeUntil
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(com.shahzadian.profilescreen.R.drawable.ic_manage_subscription_current_plan_google_play),
                contentDescription = "Google Play",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.manage_subscription_current_plan_premium_info),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }

    }
}

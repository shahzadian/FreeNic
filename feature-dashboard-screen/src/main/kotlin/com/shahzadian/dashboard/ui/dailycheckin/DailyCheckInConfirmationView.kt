package com.shahzadian.dashboard.ui.dailycheckin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground

@Composable
internal fun DailyCheckInConfirmationView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.dashboard_daily_check_in_confirmation),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

package com.shahzadian.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R

@Composable
internal fun DashboardWantToSmokeView(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onClick = remember(Unit) { { onClicked() } }
    Column(
        modifier = modifier
            .height(100.dp)
            .background(
                color = MaterialTheme.colorScheme.error,
                shape = MaterialTheme.shapes.large,
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(com.shahzadian.dashboard.R.drawable.ic_dashboard_want_to_smoke),
                contentDescription = "smoke",
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(
                text = stringResource(R.string.dashboard_want_to_smoke_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

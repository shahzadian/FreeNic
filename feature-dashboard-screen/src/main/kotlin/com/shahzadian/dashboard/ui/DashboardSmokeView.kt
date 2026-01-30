package com.shahzadian.dashboard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.shahzadian.dashboard.R

@Composable
internal fun DashboardSmokeView(
    onWantToSmokeClicked: () -> Unit,
    onIsSmokedClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val wantToSmokeClick = remember(Unit) { { onWantToSmokeClicked() } }
    val isSmokedClick = remember(Unit) { { onIsSmokedClicked() } }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .mainCardBackground()
            .padding(16.dp)
    ) {
        HeaderView(
            modifier = Modifier
                .padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        WantToSmokeButton(
            onClick = wantToSmokeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ISmokedButton(
            onClick = isSmokedClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }

}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_smoke_view_2),
            contentDescription = "Support",
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.dashboard_smoke_view_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.dashboard_smoke_view_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}

@Composable
private fun WantToSmokeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_smoke_view_want_to_smoke),
            contentDescription = "Want to smoke",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.dashboard_want_to_smoke_title),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ISmokedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.dashboard_i_smoked_title),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
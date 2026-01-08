package com.shahzadian.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
internal fun CravingsView(
    onHelpClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onHelpClick = remember(onHelpClicked) { { onHelpClicked() } }
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large,
            )
            .padding(16.dp),
    ) {
        HeaderView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.dashboard_craving_button),
            onClick = onHelpClick,
            containerColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(com.shahzadian.dashboard.R.drawable.ic_dashboard_cravings),
            contentDescription = "Cravings",
            modifier = Modifier
                .padding(end = 8.dp)
        )
        TextView()
    }
}

@Composable
private fun TextView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.dashboard_craving_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        Text(
            text = stringResource(R.string.dashboard_craving_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

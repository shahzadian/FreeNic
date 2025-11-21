package com.shahzadian.dashboard.ui.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.header.model.HeaderUiModel

@Composable
internal fun HeaderView(
    model: HeaderUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextView(
            userName = model.userName,
            modifier = Modifier.weight(3f),
        )
        Image(
            painter = painterResource(com.shahzadian.dashboard.R.drawable.ic_dashboard_header),
            contentDescription = "dashboard header",
            modifier = Modifier.weight(1f),
            alignment = Alignment.CenterEnd,
        )
    }
}

@Composable
private fun TextView(
    userName: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.dashboard_header_title, userName),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Visible
        )
        Text(
            text = stringResource(R.string.dashboard_header_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
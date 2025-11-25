package com.shahzadian.cravingsscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.baseui.R
import com.shahzadian.cravingsscreen.ui.viewmodel.CravingsViewState

@Composable
internal fun CravingsToolsView(
    data: CravingsViewState.Data,
    modifier: Modifier = Modifier,
) {
    val onAllClick = remember(data) {
        { data.onAllButtonClick() }
    }
    Column(
        modifier = modifier.padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.cravings_tools_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        data.tools.forEach { tool -> CravingToolView(tool) }
        if (data.showAllButton) {
            Text(
                text = stringResource(R.string.cravings_tools_title_see_more),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = onAllClick
                    )
            )
        }
    }
}

@Composable
private fun CravingToolView(
    tool: CravingsViewState.Data.CravingsToolUiModel,
    modifier: Modifier = Modifier,
) {
    val title = textFromWrapper(tool.title)
    val onClick = remember(tool) {
        { tool.onClick() }
    }
    Card(
        modifier = modifier
            .padding(bottom = 16.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = imageFromWrapper(tool.icon),
                contentDescription = title,
                modifier = Modifier
                    .weight(0.5f)

            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = textFromWrapper(tool.subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Image(
                painter = painterResource(com.shahzadian.cravingsscreen.R.drawable.ic_cravings_tool_arrow),
                contentDescription = "Arrow",
                modifier = Modifier.weight(0.3f)
            )
        }
    }
}

package com.shahzadian.insightsscreen.ui.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState

@Composable
internal fun ChartBadgeView(
    badgeUiModel: InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel.BadgeUiModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.small
            )
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Text(
                text = textFromWrapper(badgeUiModel.text),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

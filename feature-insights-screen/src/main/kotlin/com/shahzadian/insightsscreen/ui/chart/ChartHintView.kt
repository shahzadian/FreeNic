package com.shahzadian.insightsscreen.ui.chart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState

@Composable
internal fun ChartHintView(
    hintUiModel: InsightsViewState.ChartUiModel.TitleAndPercentsChartUiModel.HintUiModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.small
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Image(
                painter = imageFromWrapper(hintUiModel.icon),
                contentDescription = "chart hint icon",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = textFromWrapper(hintUiModel.text),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

package com.shahzadian.insightsscreen.ui.chart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.screenHeight
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars

@Composable
internal fun SlipCaseChart(
    uiModel: InsightsViewState.ChartUiModel.SlipCasesChartUiModel,
    modifier: Modifier = Modifier,
) {
    val chartBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary,
        )
    )
    ColumnChart(
        data = remember {
            uiModel.items.map {
                Bars(
                    label = it.label,
                    values = listOf(
                        Bars.Data(
                            value = it.value.toDouble(),
                            color = chartBrush,
                        )
                    )
                )
            }
        },
        barProperties = BarProperties(
            spacing = 1.dp,
            thickness = 10.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight.dp / 2),
    )
}

package com.shahzadian.insightsscreen.ui.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.screenHeight
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties

@Composable
internal fun SlipCaseChart(
    uiModel: InsightsViewState.ChartUiModel.SlipCasesChartUiModel,
    onLogSlipClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val logSlipClick = remember { { onLogSlipClicked() } }
    val slipBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary,
        )
    )
    val cravingBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.tertiary,
        )
    )
    if (uiModel.items.all { it.fixedSlipsCount == 0 && it.fixedCravingsCount == 0 }) {
        EmptyView(
            onLogSlipClicked = logSlipClick,
            modifier = modifier,
        )
        return
    }
    ColumnChart(
        data = remember {
            uiModel.items.map {
                Bars(
                    label = it.label,
                    values = listOf(
                        Bars.Data(
                            value = it.fixedSlipsCount.toDouble(),
                            color = slipBrush,
                        ),
                        Bars.Data(
                            value = it.fixedCravingsCount.toDouble(),
                            color = cravingBrush,
                        )
                    )
                )
            }
        },
        barProperties = BarProperties(
            spacing = 1.dp,
            thickness = 20.dp,
            cornerRadius = Bars.Data.Radius.Circular(
                radius = 4.dp
            )
        ),
        labelHelperProperties = LabelHelperProperties(
            enabled = false,
        ),
        labelProperties = LabelProperties(
            enabled = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
        ),
        indicatorProperties = HorizontalIndicatorProperties(
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight.dp / 2),
    )
}

@Composable
private fun EmptyView(
    onLogSlipClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val logSlipClick = remember { { onLogSlipClicked() } }
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.insight_chart_slips_empty_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.insight_chart_slips_empty_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.insight_chart_slips_empty_add_slip_button),
            onClick = logSlipClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

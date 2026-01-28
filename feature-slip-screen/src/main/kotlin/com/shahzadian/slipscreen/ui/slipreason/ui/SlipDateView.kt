package com.shahzadian.slipscreen.ui.slipreason.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.AppDatePickerDialog
import com.freenic.ui.DatePickerResult
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.slipscreen.R
import com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel.SlipReasonViewState

@Composable
internal fun SlipDateView(
    data: SlipReasonViewState.SlipDateUiModel,
    onDateConfirmed: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isPickerShown by remember { mutableStateOf(false) }
    val onDismiss = remember { { isPickerShown = false } }
    val onConfirmed: (DatePickerResult) -> Unit =
        remember {
            { result ->
                isPickerShown = false
                onDateConfirmed(result.millis)
            }
        }
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.large,
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.large,
            )
            .clickable {
                isPickerShown = true
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_slip_reason_calendar),
            contentDescription = "Calendar",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = textFromWrapper(data.displayableDate),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_slip_reason_calendar_arrow),
            contentDescription = "Calendar arrow",
        )
    }

    if (isPickerShown) {
        AppDatePickerDialog(
            initialDateMillis = data.millis,
            maxDateMillis = System.currentTimeMillis(),
            minDateMillis = data.minimalSlipDateMillis,
            onDismissRequest = onDismiss,
            onDateConfirmed = onConfirmed,
        )
    }
}

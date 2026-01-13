package com.shahzadian.datafill.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.freenic.ui.AppDatePickerDialog
import com.freenic.ui.DatePickerResult
import com.freenic.ui.extensions.noIndicationClick
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.datafill.R
import com.shahzadian.datafill.ui.DataFillViewState

@Composable
internal fun DataFillPackPricePage(
    data: DataFillViewState.PackPrice,
    onPriceChanged: (price: String) -> Unit,
    onQuitDateChanged: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val quitDateChanged = remember { { millis: Long -> onQuitDateChanged(millis) } }
    Column(
        modifier = modifier
            .noIndicationClick {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
    ) {
        DataFillHeader(
            title = TextWrapper.FromRes(com.shahzadian.baseui.R.string.data_fill_price_title),
            subtitle = TextWrapper.FromRes(com.shahzadian.baseui.R.string.data_fill_price_subtitle),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp)
        )
        DataFillFieldBlock(
            label = TextWrapper.FromRes(com.shahzadian.baseui.R.string.data_fill_price_label),
            inputField = {
                InputField(
                    priceData = data.price,
                    onPriceChanged = onPriceChanged,
                    modifier = it,
                )
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        DataFillFieldBlock(
            label = TextWrapper.FromRes(com.shahzadian.baseui.R.string.fillable_info_pack_price_quit_date),
            inputField = {
                QuitDateView(
                    data = data.quitData,
                    onDateConfirmed = quitDateChanged,
                    modifier = it.fillMaxWidth(),
                )
            },
        )
    }
}

@Composable
private fun InputField(
    priceData: DataFillViewState.PackPrice.PriceData?,
    onPriceChanged: (price: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        val price = priceData?.price ?: ""
        DataFillInputField(
            value = price,
            hint = stringResource(com.shahzadian.baseui.R.string.data_fill_price_input_hint),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                onPriceChanged(it)
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun QuitDateView(
    data: DataFillViewState.PackPrice.QuitData,
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
            painter = painterResource(R.drawable.ic_data_fill_calendar),
            contentDescription = "Calendar",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = textFromWrapper(data.displayableQuitDate),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_data_fill_calendar_arrow),
            contentDescription = "Calendar arrow",
        )
    }

    if (isPickerShown) {
        AppDatePickerDialog(
            initialDateMillis = data.quitDateMillis,
            maxDateMillis = System.currentTimeMillis(),
            onDismissRequest = onDismiss,
            onDateConfirmed = onConfirmed,
        )
    }
}

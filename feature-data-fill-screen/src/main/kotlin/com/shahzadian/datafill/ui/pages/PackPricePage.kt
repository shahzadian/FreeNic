package com.shahzadian.datafill.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.noIndicationClick
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.datafill.ui.DataFillViewState

@Composable
internal fun DataFillPackPricePage(
    data: DataFillViewState.PackPrice,
    onPriceChanged: (price: String, currency: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
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
            }
        )
    }
}

@Composable
private fun InputField(
    priceData: DataFillViewState.PackPrice.PriceData?,
    onPriceChanged: (price: String, currency: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        val price = priceData?.price ?: ""
        val currencies = priceData?.currencies ?: emptyList()
        var selectedCurrency by remember { mutableStateOf(priceData?.currency ?: "") }
        // Keep local currency in sync with state updates
        LaunchedEffect(priceData?.currency) {
            if (priceData?.currency != null && priceData.currency != selectedCurrency) {
                selectedCurrency = priceData.currency
            }
        }

        var isMenuExpanded by remember { mutableStateOf(false) }
        DataFillInputField(
            value = price,
            hint = stringResource(com.shahzadian.baseui.R.string.data_fill_price_input_hint),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                onPriceChanged(it, selectedCurrency)
            },
            modifier = Modifier.fillMaxWidth(),
            trailingContent = {
                Box {
                    RowCurrencyPicker(
                        currency = selectedCurrency,
                        onClick = { isMenuExpanded = true }
                    )
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                    ) {
                        currencies.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    isMenuExpanded = false
                                    if (item != selectedCurrency) {
                                        selectedCurrency = item
                                        onPriceChanged(price, selectedCurrency)
                                    }
                                },
                                modifier = Modifier.background(
                                    color = if (item == selectedCurrency) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                                )
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun RowCurrencyPicker(
    currency: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currency,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

package com.shahzadian.datafill.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.datafill.ui.DataFillViewState

@Composable
internal fun DataFillAboutPage(
    data: DataFillViewState.About,
    onNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState),
    ) {
        DataFillHeader(
            title = TextWrapper.FromRes(R.string.data_fill_about_title),
            subtitle = TextWrapper.FromRes(R.string.data_fill_about_subtitle),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp)
        )
        NameBlock(
            value = data.name ?: "",
            onValueChange = onNameChanged,
            modifier = Modifier
                .padding(bottom = 24.dp),
        )
        AgeBlock(
            value = data.age ?: "",
            onValueChange = onAgeChanged,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )
        ConfidentialInfoView(
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun NameBlock(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FieldBlock(
        label = TextWrapper.FromRes(R.string.data_fill_about_field_name_label),
        description = TextWrapper.FromRes(R.string.data_fill_about_field_name_desc),
        inputField = { inputModifier ->
            DataFillInputField(
                value = value,
                hint = stringResource(R.string.data_fill_about_field_name_hint),
                onValueChange = onValueChange,
                hasNextField = true,
                modifier = inputModifier,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun AgeBlock(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FieldBlock(
        label = TextWrapper.FromRes(R.string.data_fill_about_field_age_label),
        description = TextWrapper.FromRes(R.string.data_fill_about_field_age_desc),
        inputField = { inputModifier ->
            DataFillInputField(
                value = value,
                hint = stringResource(R.string.data_fill_about_field_age_hint),
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                hasNextField = false,
                modifier = inputModifier,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun FieldBlock(
    label: TextWrapper,
    description: TextWrapper,
    inputField: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        DataFillInputLabel(
            text = label,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        inputField(Modifier.fillMaxWidth())
        DataFillInputDescription(
            text = description,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun ConfidentialInfoView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(65.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.1f)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(com.shahzadian.datafill.R.drawable.ic_data_fill_about_lock),
            contentDescription = "Confidential info",
            modifier = Modifier
                .padding(end = 12.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = stringResource(R.string.data_fill_about_confidential_info),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}
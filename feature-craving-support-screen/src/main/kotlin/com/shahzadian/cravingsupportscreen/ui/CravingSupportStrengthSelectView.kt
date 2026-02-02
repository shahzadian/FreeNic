package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewState

@Composable
internal fun CravingSupportStrengthSelectView(
    selectedStrength: CravingSupportViewState.CravingStrengthUiModel?,
    onStrengthSelected: (CravingSupportViewState.CravingStrengthUiModel) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val submitClick = remember { { onSubmit() } }
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.craving_support_craving_strength),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StrengthView(
                strength = CravingSupportViewState.CravingStrengthUiModel.Low,
                onSelected = { onStrengthSelected(CravingSupportViewState.CravingStrengthUiModel.Low) },
                isSelected = selectedStrength == CravingSupportViewState.CravingStrengthUiModel.Low,
                modifier = Modifier.weight(1f),
            )
            StrengthView(
                strength = CravingSupportViewState.CravingStrengthUiModel.Medium,
                onSelected = { onStrengthSelected(CravingSupportViewState.CravingStrengthUiModel.Medium) },
                isSelected = selectedStrength == CravingSupportViewState.CravingStrengthUiModel.Medium,
                modifier = Modifier.weight(1f),
            )
            StrengthView(
                strength = CravingSupportViewState.CravingStrengthUiModel.Strong,
                onSelected = { onStrengthSelected(CravingSupportViewState.CravingStrengthUiModel.Strong) },
                isSelected = selectedStrength == CravingSupportViewState.CravingStrengthUiModel.Strong,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        MainSubmitButton(
            text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.craving_support_strength_submit),
            onClick = submitClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun StrengthView(
    strength: CravingSupportViewState.CravingStrengthUiModel,
    onSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val selectedClick = remember(strength) { { onSelected() } }
    Column(
        modifier = modifier
            .clickable(onClick = selectedClick)
            .mainCardBackground()
            .then(
                if (isSelected) {
                    Modifier
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = MaterialTheme.shapes.medium,
                        )
                } else {
                    Modifier
                }
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = strength.emoji,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = stringResource(strength.labelRes),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewState

@Composable
internal fun CravingSupportStrengthSelectView(
    selectedStrength: CravingSupportViewState.CravingStrength?,
    onStrengthSelected: (CravingSupportViewState.CravingStrength) -> Unit,
    modifier: Modifier = Modifier,
) {
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
                strength = CravingSupportViewState.CravingStrength.Low,
                onSelected = { onStrengthSelected(CravingSupportViewState.CravingStrength.Low) },
                isSelected = selectedStrength == CravingSupportViewState.CravingStrength.Low,
                modifier = Modifier.weight(1f),
            )
            StrengthView(
                strength = CravingSupportViewState.CravingStrength.Middle,
                onSelected = { onStrengthSelected(CravingSupportViewState.CravingStrength.Middle) },
                isSelected = selectedStrength == CravingSupportViewState.CravingStrength.Middle,
                modifier = Modifier.weight(1f),
            )
            StrengthView(
                strength = CravingSupportViewState.CravingStrength.Strong,
                onSelected = { onStrengthSelected(CravingSupportViewState.CravingStrength.Strong) },
                isSelected = selectedStrength == CravingSupportViewState.CravingStrength.Strong,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun StrengthView(
    strength: CravingSupportViewState.CravingStrength,
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

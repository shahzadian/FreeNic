package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainHintBackground

@Composable
internal fun CravingSupportStrengthSavedView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .mainHintBackground()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.craving_support_strength_successfully_saved),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
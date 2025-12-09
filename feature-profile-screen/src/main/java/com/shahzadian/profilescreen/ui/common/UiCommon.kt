package com.shahzadian.profilescreen.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.profilescreen.R

@Composable
internal fun ProfileSettingsBlockView(
    title: TextWrapper,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp),
    ) {
        Text(
            text = textFromWrapper(title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        content()
    }
}

@Composable
internal fun ProfileSettingsField(
    label: TextWrapper,
    value: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            FieldText(
                label = label,
                value = value,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(R.drawable.ic_profile_edit),
                contentDescription = "edit",
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
        )
    }
}

@Composable
private fun FieldText(
    label: TextWrapper,
    value: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = textFromWrapper(label),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = textFromWrapper(value),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
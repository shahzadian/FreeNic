package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.shahzadian.profilescreen.R

@Composable
internal fun ProfileClearAllDataView(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val click = remember { { onClicked() } }
    var shouldShowDialog by remember { mutableStateOf(false) }
    if (shouldShowDialog) {
        AlertDialog(
            onDismissRequest = { shouldShowDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        click()
                        shouldShowDialog = false
                    },
                ) {
                    Text(
                        text = stringResource(com.shahzadian.baseui.R.string.profile_clear_all_data_confirmation_submit)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { shouldShowDialog = false }
                ) {
                    Text(
                        text = stringResource(com.shahzadian.baseui.R.string.profile_clear_all_data_confirmation_cancel),
                    )
                }
            },
            title = {
                Text(text = stringResource(com.shahzadian.baseui.R.string.profile_clear_all_data_confirmation_title))
            },
            text = {
                Text(
                    text = stringResource(com.shahzadian.baseui.R.string.profile_clear_all_data_confirmation_subtitle),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        )
    }
    Row(
        modifier = modifier
            .mainCardBackground()
            .clickable {
                shouldShowDialog = true
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(end = 4.dp)
                .background(
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape,
                )
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_profile_clear_all_data),
                contentDescription = "clear all data",
                modifier = Modifier
            )
        }
        Column {
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.profile_clear_all_data_title),
                modifier = Modifier.padding(bottom = 4.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.profile_clear_all_data_subtitle),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

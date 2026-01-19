package com.shahzadian.profilescreen.managesubscription

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.freenic.ui.extensions.noIndicationClick
import com.shahzadian.baseui.R

@Composable
internal fun CancelSubscriptionView(
    activeUntilDate: String,
    onCancelConfirmed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var shouldShowDialog by remember { mutableStateOf(false) }
    val cancelConfirm = remember { { onCancelConfirmed() } }
    if (shouldShowDialog) {
        AlertDialog(
            onDismissRequest = { shouldShowDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        cancelConfirm()
                        shouldShowDialog = false
                    },
                ) {
                    Text(
                        text = stringResource(R.string.manage_subscription_cancel_confirmation_confirm)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { shouldShowDialog = false }
                ) {
                    Text(
                        text = stringResource(R.string.manage_subscription_cancel_confirmation_cancel),
                    )
                }
            },
            title = {
                Text(text = stringResource(R.string.manage_subscription_cancel_confirmation_title))
            },
            text = {
                Text(
                    text = stringResource(R.string.manage_subscription_cancel_confirmation_subtitle, activeUntilDate),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        )
    }
    Text(
        text = stringResource(R.string.manage_subscription_cancel_button),
        color = MaterialTheme.colorScheme.onSecondary,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .noIndicationClick {
                shouldShowDialog = true
            }
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

package com.shahzadian.push.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.noIndicationClick
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.push.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PushShowDialog(
    isSoftAsk: Boolean,
    onSubmitClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                showDialog = false
            }
        ) {
            PushShowView(
                isSoftAsk = isSoftAsk,
                onSubmitClicked = onSubmitClicked,
                onCancelClicked = {
                    showDialog = false
                    onCancelClicked()
                },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun PushShowView(
    isSoftAsk: Boolean,
    onSubmitClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val submitClick = remember { { onSubmitClicked() } }
    val cancelClick = remember { { onCancelClicked() } }
    val icon = if (isSoftAsk) {
        R.drawable.ic_push_soft
    } else {
        R.drawable.ic_push_default
    }
    val title = if (isSoftAsk) {
        com.shahzadian.baseui.R.string.push_soft_ask_title
    } else {
        com.shahzadian.baseui.R.string.push_default_title
    }
    val subtitle = if (isSoftAsk) {
        com.shahzadian.baseui.R.string.push_soft_ask_subtitle
    } else {
        com.shahzadian.baseui.R.string.push_default_subtitle
    }
    val submitText = if (isSoftAsk) {
        com.shahzadian.baseui.R.string.push_soft_ask_submit
    } else {
        com.shahzadian.baseui.R.string.push_default_submit
    }
    val cancelText = if (isSoftAsk) {
        com.shahzadian.baseui.R.string.push_soft_ask_cancel
    } else {
        com.shahzadian.baseui.R.string.push_default_cancel
    }
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "Push default image",
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center,
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(submitText),
            onClick = submitClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(cancelText),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.noIndicationClick(onClick = cancelClick)
        )
    }
}

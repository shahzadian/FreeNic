package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewState

@Composable
internal fun CravingSupportActivityView(
    uiModel: CravingSupportViewState.CravingSupportActivityUiModel,
    onClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.craving_support_activity_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
                .padding(bottom = 16.dp)
                .clickable {
                    onClicked(uiModel.id)
                }
        )
        ContentView(
            uiModel = uiModel,
            onClicked = onClicked,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ContentView(
    uiModel: CravingSupportViewState.CravingSupportActivityUiModel,
    onClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val click = remember { { onClicked(uiModel.id) } }
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        HeaderView(
            uiModel = uiModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.craving_support_activity_submit_button),
            onClick = click,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HeaderView(
    uiModel: CravingSupportViewState.CravingSupportActivityUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        Image(
            painter = imageFromWrapper(uiModel.icon),
            contentDescription = "Header image",
            modifier = Modifier
                .size(50.dp)
                .padding(end = 8.dp)
        )
        Column {
            Text(
                text = textFromWrapper(uiModel.name),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = textFromWrapper(uiModel.description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}

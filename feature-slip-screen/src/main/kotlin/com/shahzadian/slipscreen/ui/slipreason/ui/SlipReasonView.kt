package com.shahzadian.slipscreen.ui.slipreason.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel.SlipReasonViewState

@Composable
internal fun SlipReasonView(
    data: SlipReasonViewState.SlipReasonUiModel,
    isSelected: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val click = remember(Unit) { { onClicked() } }
    Row(
        modifier = modifier
            .let {
                if (isSelected) {
                    it.mainCardBackground()
                } else {
                    it
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = MaterialTheme.shapes.medium
                        )
                }
            }
            .clickable(onClick = click)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = imageFromWrapper(data.icon),
            contentDescription = "icon",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = textFromWrapper(data.name),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

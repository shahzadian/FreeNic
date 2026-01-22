package com.shahzadian.insightsscreen.ui.chart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Composable
internal fun ChartTabView(
    title: TextWrapper,
    icon: ImageWrapper,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                },
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = imageFromWrapper(icon),
            contentDescription = "Header image",
            colorFilter = ColorFilter.tint(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            ),
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = textFromWrapper(title),
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

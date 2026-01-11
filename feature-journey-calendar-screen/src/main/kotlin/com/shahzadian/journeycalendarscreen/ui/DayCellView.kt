package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayCellUiModel

@Composable
internal fun DayCellView(
    onDayClicked: () -> Unit,
    cell: DayCellUiModel,
) {
    val dayClick = remember { { onDayClicked() } }
    val background = when {
        cell.isToday -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.25f)
        cell.inCurrentMonth -> MaterialTheme.colorScheme.surface
        else -> Color.Transparent
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = background, shape = MaterialTheme.shapes.small,
            )
            .then(
                if (cell.inCurrentMonth) Modifier.clickable(onClick = dayClick) else Modifier
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = cell.dayText?.let { textFromWrapper(it) } ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = when {
                !cell.inCurrentMonth -> MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f)
                else -> MaterialTheme.colorScheme.onPrimary
            },
            textAlign = TextAlign.Center,
        )
    }
}

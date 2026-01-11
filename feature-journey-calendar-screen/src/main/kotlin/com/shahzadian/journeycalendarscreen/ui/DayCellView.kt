package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
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
        cell.isInCurrentMonth -> MaterialTheme.colorScheme.surface
        else -> Color.Transparent
    }
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = background, shape = MaterialTheme.shapes.small,
            )
            .then(
                if (cell.isInCurrentMonth) Modifier.clickable(onClick = dayClick) else Modifier
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = cell.dayText?.let { textFromWrapper(it) } ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = when {
                !cell.isInCurrentMonth -> MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f)
                else -> MaterialTheme.colorScheme.onPrimary
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = if (cell.events.isNotEmpty()) 4.dp else 0.dp)
        )
        if (cell.isInCurrentMonth) {
            cell.events.forEachIndexed { index, model ->
                Image(
                    painter = imageFromWrapper(model.icon),
                    contentDescription = "Event ${index + 1}",
                    modifier = Modifier.padding(bottom = if (index < cell.events.lastIndex) 2.dp else 0.dp)
                )
            }
        }
    }
}

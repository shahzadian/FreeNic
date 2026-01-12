package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier,
) {
    val background = when {
        cell.isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
        cell.isInCurrentMonth -> MaterialTheme.colorScheme.surface
        else -> Color.Transparent
    }
    Column(
        modifier = modifier
            .background(
                color = background, shape = MaterialTheme.shapes.small,
            )
            .then(
                if (cell.isInCurrentMonth) Modifier.clickable(onClick = onDayClicked) else Modifier
            )
            .then(
                if (cell.isInSmokeQuitPeriod) {
                    Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small,
                        )
                } else Modifier
            )
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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

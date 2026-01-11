package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.journeycalendarscreen.ui.viewmodel.JourneyCalendarViewState

@Composable
internal fun DayCellView(
    cell: JourneyCalendarViewState.Data.DayCellUi,
) {
    val background = when {
        cell.isSelected -> MaterialTheme.colorScheme.primary
        cell.isToday -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.25f)
        else -> Color.Transparent
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(background, shape = MaterialTheme.shapes.small)
            .then(
                if (cell.isEnabled && cell.onClick != null) Modifier.clickable { cell.onClick.invoke() } else Modifier
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = cell.dayText?.let { textFromWrapper(it) } ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = if (cell.isSelected) FontWeight.Bold else FontWeight.Normal),
            color = when {
                !cell.inCurrentMonth -> MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f)
                cell.isSelected -> MaterialTheme.colorScheme.onPrimary
                else -> MaterialTheme.colorScheme.onPrimary
            },
            textAlign = TextAlign.Center,
        )
    }
}
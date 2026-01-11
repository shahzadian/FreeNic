package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.journeycalendarscreen.R

@Composable
internal fun CalendarHeaderView(
    title: TextWrapper,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_journey_calendar_arrow_prev),
            contentDescription = "prev",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onPrev)
        )

        Text(
            text = textFromWrapper(title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        Icon(
            painter = painterResource(R.drawable.ic_journey_calendar_arrow_next),
            contentDescription = "next",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onNext)
        )
    }
}
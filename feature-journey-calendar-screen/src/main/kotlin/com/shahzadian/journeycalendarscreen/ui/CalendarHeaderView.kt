package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.journeycalendarscreen.R

@Composable
internal fun CalendarHeaderView(
    title: TextWrapper,
    onPrevClicked: () -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val nextClick = remember { { onNextClicked() } }
    val prevClick = remember { { onPrevClicked() } }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_journey_calendar_arrow_prev),
            contentDescription = "prev",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(onClick = prevClick),
        )

        Text(
            text = textFromWrapper(title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier,
        )

        Icon(
            painter = painterResource(R.drawable.ic_journey_calendar_arrow_next),
            contentDescription = "next",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(onClick = nextClick)
        )
    }
}
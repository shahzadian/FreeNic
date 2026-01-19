package com.shahzadian.journeycalendarscreen.ui.addnote

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.screenHeight
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
internal fun AddNoteLockedView(
    dayText: TextWrapper,
    onUnlockClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    onNotNowClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val closeClick = remember { { onCloseClicked() } }
    val notNowClick = remember { { onNotNowClicked() } }
    Box(
        modifier = modifier
            .padding(16.dp),
    ) {
        HeaderView(
            dayText = dayText,
            onCloseClicked = closeClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )
        MainContentView(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = screenHeight.dp / 8)
        )
        FooterView(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onUnlockClicked = onUnlockClicked,
            onNotNowClicked = notNowClick,
        )
    }
}

@Composable
private fun HeaderView(
    dayText: TextWrapper,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val closeClick = remember { { onCloseClicked() } }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = textFromWrapper(dayText),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(end = 4.dp)
                .weight(1f)
        )
        Image(
            painter = painterResource(com.shahzadian.journeycalendarscreen.R.drawable.ic_journey_calendar_events_bottom_sheet_close),
            contentDescription = "Close bottom sheet",
            modifier = Modifier.clickable(onClick = closeClick)
        )
    }
}

@Composable
private fun MainContentView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        MainTextView(
            modifier = Modifier
                .padding(bottom = 32.dp)
        )
        FeaturesView()
    }
}

@Composable
private fun MainTextView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_premium),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.journey_calendar_add_note_locked_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = stringResource(R.string.journey_calendar_add_note_locked_subtitle),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun FeaturesView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        FeatureView(
            iconRes = com.shahzadian.journeycalendarscreen.R.drawable.ic_journey_calendar_add_note_1,
            titleRes = R.string.journey_calendar_add_note_note_1_title,
            subtitleRes = R.string.journey_calendar_add_note_note_1_subtitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        FeatureView(
            iconRes = com.shahzadian.journeycalendarscreen.R.drawable.ic_journey_calendar_add_note_2,
            titleRes = R.string.journey_calendar_add_note_note_2_title,
            subtitleRes = R.string.journey_calendar_add_note_note_2_subtitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        FeatureView(
            iconRes = com.shahzadian.journeycalendarscreen.R.drawable.ic_journey_calendar_add_note_3,
            titleRes = R.string.journey_calendar_add_note_note_3_title,
            subtitleRes = R.string.journey_calendar_add_note_note_3_subtitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun FeatureView(
    iconRes: Int,
    titleRes: Int,
    subtitleRes: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(20.dp)
        )
        Column {
            Text(
                text = stringResource(titleRes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = stringResource(subtitleRes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}

@Composable
private fun FooterView(
    onUnlockClicked: () -> Unit,
    onNotNowClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val unlockClick = remember { { onUnlockClicked() } }
    val notNowClick = remember { { onNotNowClicked() } }
    Column(
        modifier = modifier,
    ) {
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.journey_calendar_add_note_unlock_button),
            onClick = unlockClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.journey_calendar_add_note_not_now),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .clickable(onClick = notNowClick)
                .align(Alignment.CenterHorizontally),
        )
    }
}
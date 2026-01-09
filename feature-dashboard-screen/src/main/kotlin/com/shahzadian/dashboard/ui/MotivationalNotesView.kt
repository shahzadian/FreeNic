package com.shahzadian.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.dashboard.R
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

@Composable
internal fun MotivationalNotesView(
    note: DashboardViewState.MotivationalNoteUiModel,
    onRefreshClicked: () -> Unit,
    onSeeAllClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onRefreshClick = remember {
        { onRefreshClicked() }
    }
    val onSeeAllClick = remember {
        { onSeeAllClicked() }
    }
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        NotesView(
            note = note,
            onRefreshClicked = onRefreshClick,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        SeeAllNotesView(
            onClicked = onSeeAllClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun NotesView(
    note: DashboardViewState.MotivationalNoteUiModel,
    onRefreshClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onRefreshClick = remember {
        { onRefreshClicked() }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_helpful_notes),
            contentDescription = "Helpful notes",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = textFromWrapper(note.text),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f)
        )
        Image(
            painter = painterResource(R.drawable.ic_dashboard_motivational_notes_refresh),
            contentDescription = "Refresh",
            modifier = Modifier.clickable(onClick = onRefreshClick),
        )
    }
}

@Composable
private fun SeeAllNotesView(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onClick = remember {
        { onClicked() }
    }
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium,
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.dashboard_motivational_notes_see_all),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

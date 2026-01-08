package com.shahzadian.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.dashboard.R
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

@Composable
internal fun HelpfulNotesView(
    note: DashboardViewState.HelpfulNoteUiModel,
    onRefreshClicked: () -> Unit,
    onAddNoteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onRefreshClick = remember {
        { onRefreshClicked() }
    }
    val onAddNoteClick = remember {
        { onAddNoteClicked() }
    }
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        NotesView(
            note = note,
            onRefreshClicked = onRefreshClick,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        AddYourOwnNotesView(
            onAddNoteClicked = onAddNoteClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun NotesView(
    note: DashboardViewState.HelpfulNoteUiModel,
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
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f)
        )
        Image(
            painter = painterResource(R.drawable.ic_dashboard_helpful_notes_refresh),
            contentDescription = "Refresh",
            modifier = Modifier.clickable(onClick = onRefreshClick),
        )
    }
}

@Composable
private fun AddYourOwnNotesView(
    onAddNoteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onAddNoteClick = remember {
        { onAddNoteClicked() }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium,
            )
            .clickable(onClick = onAddNoteClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_helpful_notes_premium),
            contentDescription = "Helpful notes premium",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.dashboard_helpful_notes_add_your_own),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

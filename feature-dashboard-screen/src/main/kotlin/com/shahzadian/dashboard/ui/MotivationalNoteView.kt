package com.shahzadian.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
internal fun MotivationalNoteView(
    noteUiModel: DashboardViewState.MotivationalNoteUiModel,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onClick = remember(Unit) { { onClicked() } }
    Column(
        modifier = modifier
            .mainCardBackground()
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        NoteTexView(
            noteUiModel = noteUiModel,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        RefreshView()
    }
}

@Composable
private fun NoteTexView(
    noteUiModel: DashboardViewState.MotivationalNoteUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_motiviational_note),
            contentDescription = "Note",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = textFromWrapper(noteUiModel.text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun RefreshView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dashboard_motivational_note_refresh),
            contentDescription = "Refresh",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.dashboard_motivational_note_show_another),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

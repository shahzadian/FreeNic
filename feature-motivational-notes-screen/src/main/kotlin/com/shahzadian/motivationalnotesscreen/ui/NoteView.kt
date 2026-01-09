package com.shahzadian.motivationalnotesscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.motivationalnotesscreen.R
import com.shahzadian.motivationalnotesscreen.ui.viewmodel.MotivationalNotesViewState

@Composable
internal fun NoteView(
    data: MotivationalNotesViewState.MotivationalNoteUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        BadgeView(
            text = textFromWrapper(data.source.getBadgeText()),
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 16.dp),
        )
        NoteTextView(
            text = data.text,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun BadgeView(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_motivational_note_badge),
            contentDescription = "Badge",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun NoteTextView(
    text: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Text(
        text = textFromWrapper(text),
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.titleLarge
    )
}
